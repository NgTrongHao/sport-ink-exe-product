package rubberduck.org.sportinksystemalt.chatting.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import rubberduck.org.sportinksystemalt.chatting.domain.dto.ChatGroupResponse;
import rubberduck.org.sportinksystemalt.chatting.domain.dto.ChatMessageResponse;
import rubberduck.org.sportinksystemalt.chatting.domain.entity.ChatGroup;
import rubberduck.org.sportinksystemalt.chatting.domain.entity.ChatMessage;
import rubberduck.org.sportinksystemalt.chatting.repository.ChatGroupRepository;
import rubberduck.org.sportinksystemalt.chatting.repository.ChatMessageRepository;
import rubberduck.org.sportinksystemalt.chatting.service.IChatService;
import rubberduck.org.sportinksystemalt.playtime.domain.entity.Playtime;
import rubberduck.org.sportinksystemalt.playtime.service.IPlaytimeService;
import rubberduck.org.sportinksystemalt.shared.service.cache.CacheService;
import rubberduck.org.sportinksystemalt.user.service.IUserService;

import java.util.List;
import java.util.UUID;

@Service
public class ChatService implements IChatService {
    private final ChatGroupRepository chatGroupRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final IPlaytimeService playtimeService;
    private final IUserService userService;
    private final CacheService cacheService;
    private final SimpMessagingTemplate messagingTemplate;
    private final ObjectMapper objectMapper;

    public ChatService(ChatGroupRepository chatGroupRepository, ChatMessageRepository chatMessageRepository, IPlaytimeService playtimeService, IUserService userService, CacheService cacheService, SimpMessagingTemplate messagingTemplate, ObjectMapper objectMapper) {
        this.chatGroupRepository = chatGroupRepository;
        this.chatMessageRepository = chatMessageRepository;
        this.playtimeService = playtimeService;
        this.userService = userService;
        this.cacheService = cacheService;
        this.messagingTemplate = messagingTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    public List<ChatGroupResponse> getMyChatGroups(String username) {
        List<Playtime> playtimes = playtimeService.getAllPlaytimesOfUser(username);
        List<ChatGroup> chatGroups = chatGroupRepository.findAllByPlaytimeIn(playtimes);
        return chatGroups.stream()
                .filter(chatGroup -> !chatGroup.isDeleted())
                .map(this::mapToChatGroupResponse).toList();
    }

    @Override
    public List<ChatMessageResponse> getChatMessages(String username, UUID groupId, int page, int pageSize) {
        ChatGroup chatGroup = validateChatGroupMembership(username, groupId);
        List<ChatMessageResponse> cachedMessages = getCachedMessages(groupId);
        if (cachedMessages != null) {
            return cachedMessages;
        }

        Pageable pageable = PageRequest.of(page - 1, pageSize);
        List<ChatMessage> chatMessages = chatMessageRepository.findAllByChatGroupOrderBySentAtDesc(chatGroup, pageable);
        List<ChatMessageResponse> chatMessageResponses = chatMessages.stream()
                .map(this::mapToChatMessageResponse).toList();

        cacheService.put("chatMessages::" + groupId, chatMessageResponses, 900000);
        return chatMessageResponses;
    }

    @Override
    public ChatMessageResponse sendMessage(String username, UUID groupId, String message) {
        validateMessageContent(message);
        ChatGroup chatGroup = validateChatGroupMembership(username, groupId);

        ChatMessage chatMessage = createChatMessage(chatGroup, message, username);
        ChatMessage savedChatMessage = chatMessageRepository.save(chatMessage);
        ChatMessageResponse response = mapToChatMessageResponse(savedChatMessage);

        messagingTemplate.convertAndSend("/topic/chat/" + groupId, response);
        updateCachedMessages(groupId, response);

        return response;
    }

    @Override
    public void createChatGroup(Playtime playtime) {
        ChatGroup chatGroup = ChatGroup.builder()
                .playtime(playtime)
                .participants(playtime.getParticipants())
                .build();
        chatGroupRepository.save(chatGroup);
    }

    private ChatGroup validateChatGroupMembership(String username, UUID groupId) {
        ChatGroup chatGroup = chatGroupRepository.findById(groupId)
                .orElseThrow(() -> new IllegalArgumentException("Chat group not found"));
        if (!playtimeService.getAllPlaytimesOfUser(username).contains(chatGroup.getPlaytime())) {
            throw new IllegalArgumentException("You are not a member of this chat group");
        }
        return chatGroup;
    }

    private void validateMessageContent(String message) {
        if (message == null || message.trim().isEmpty()) {
            throw new IllegalArgumentException("Message content cannot be null or empty");
        }
    }

    private ChatMessage createChatMessage(ChatGroup chatGroup, String message, String username) {
        return ChatMessage.builder()
                .chatGroup(chatGroup)
                .message(message)
                .sender(userService.findUserByUsername(username))
                .build();
    }

    private void updateCachedMessages(UUID groupId, ChatMessageResponse response) {
        List<ChatMessageResponse> cachedMessages = getCachedMessages(groupId);
        if (cachedMessages != null) {
            cachedMessages.addFirst(response);
            cacheService.put("chatMessages::" + groupId, cachedMessages, 900000);
        }
    }

    private List<ChatMessageResponse> getCachedMessages(UUID groupId) {
        String cachedData = (String) cacheService.get("chatMessages::" + groupId);
        if (cachedData == null) {
            return null;
        }
        try {
            return objectMapper.readValue(cachedData, new TypeReference<>() {
            });
        } catch (Exception e) {
            throw new RuntimeException("Error deserializing cached data", e);
        }
    }

    private ChatMessageResponse mapToChatMessageResponse(ChatMessage chatMessage) {
        return ChatMessageResponse.builder()
                .id(chatMessage.getChatMessageId())
                .message(chatMessage.getMessage())
                .sender(userService.getUserProfile(chatMessage.getSender().getUsername()))
                .sentAt(chatMessage.getSentAt())
                .build();
    }

    private ChatGroupResponse mapToChatGroupResponse(ChatGroup chatGroup) {
        return ChatGroupResponse.builder()
                .id(chatGroup.getChatGroupId())
                .playtime(playtimeService.getPlaytimeById(chatGroup.getPlaytime().getId()))
                .build();
    }
}

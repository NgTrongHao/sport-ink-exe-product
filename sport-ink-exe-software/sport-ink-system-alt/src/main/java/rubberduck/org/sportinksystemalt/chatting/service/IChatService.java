package rubberduck.org.sportinksystemalt.chatting.service;

import rubberduck.org.sportinksystemalt.chatting.domain.dto.ChatGroupResponse;
import rubberduck.org.sportinksystemalt.chatting.domain.dto.ChatMessageResponse;
import rubberduck.org.sportinksystemalt.playtime.domain.entity.Playtime;

import java.util.List;
import java.util.UUID;

public interface IChatService {
    List<ChatGroupResponse> getMyChatGroups(String username);

    List<ChatMessageResponse> getChatMessages(String username, UUID groupId, int page, int pageSize);

    ChatMessageResponse sendMessage(String username, UUID groupId, String message);

    void createChatGroup(Playtime playtime);
}

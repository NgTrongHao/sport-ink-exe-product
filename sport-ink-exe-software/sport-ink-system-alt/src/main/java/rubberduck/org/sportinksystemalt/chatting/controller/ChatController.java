package rubberduck.org.sportinksystemalt.chatting.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rubberduck.org.sportinksystemalt.chatting.domain.dto.ChatGroupResponse;
import rubberduck.org.sportinksystemalt.chatting.domain.dto.ChatMessageResponse;
import rubberduck.org.sportinksystemalt.chatting.service.IChatService;
import rubberduck.org.sportinksystemalt.shared.common.annotation.CurrentUser;
import rubberduck.org.sportinksystemalt.shared.domain.ApiResponse;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/chats")
@Tag(name = "Chat")
public class ChatController {
    private final IChatService chatService;

    public ChatController(IChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping("/get-my-chat-groups")
    public ResponseEntity<ApiResponse<List<ChatGroupResponse>>> getMyChatGroups(@CurrentUser String username) {
        return ResponseEntity.ok(
                ApiResponse.<List<ChatGroupResponse>>builder()
                        .code(200)
                        .message("Get my chat groups successfully")
                        .data(chatService.getMyChatGroups(username))
                        .build()
        );
    }

    @GetMapping("/{group-id}/get-chat-messages")
    public ResponseEntity<ApiResponse<List<ChatMessageResponse>>> getChatMessages(
            @CurrentUser String username,
            @PathVariable("group-id") UUID groupId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int pageSize
    ) {
        return ResponseEntity.ok(
                ApiResponse.<List<ChatMessageResponse>>builder()
                        .code(200)
                        .message("Get chat messages successfully")
                        .data(chatService.getChatMessages(username, groupId, page, pageSize))
                        .build()
        );
    }

    @PostMapping("/{group-id}/send-message")
    public ResponseEntity<ApiResponse<ChatMessageResponse>> sendMessage(
            @CurrentUser String username,
            @PathVariable("group-id") UUID groupId,
            @RequestBody String message
    ) {
        return ResponseEntity.ok(
                ApiResponse.<ChatMessageResponse>builder()
                        .code(200)
                        .message("Send message successfully")
                        .data(chatService.sendMessage(username, groupId, message))
                        .build()
        );
    }
}

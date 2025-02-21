package rubberduck.org.sportinksystemalt.chatting.domain.dto;

import lombok.Builder;
import rubberduck.org.sportinksystemalt.user.domain.dto.UserProfileResponse;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record ChatMessageResponse(
        UUID id,
        UserProfileResponse sender,
        String message,
        LocalDateTime sentAt
) implements java.io.Serializable {
}

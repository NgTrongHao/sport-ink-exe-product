package rubberduck.org.sportinksystemalt.chatting.domain.dto;

import lombok.Builder;
import rubberduck.org.sportinksystemalt.playtime.domain.dto.PlaytimeResponse;

import java.util.UUID;

@Builder
public record ChatGroupResponse(
        UUID id,
        PlaytimeResponse playtime
) implements java.io.Serializable {
}

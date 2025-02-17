package rubberduck.org.sportinksystemalt.user.domain.dto;

import lombok.Builder;

import java.io.Serializable;

@Builder
public record PlayerProfileResponse(
        String gender,
        String referenceSport
) implements Serializable {
}

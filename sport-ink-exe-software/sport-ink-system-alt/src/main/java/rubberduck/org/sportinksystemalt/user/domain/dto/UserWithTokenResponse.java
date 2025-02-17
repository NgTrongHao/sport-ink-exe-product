package rubberduck.org.sportinksystemalt.user.domain.dto;

import lombok.Builder;
import rubberduck.org.sportinksystemalt.shared.domain.AccessToken;

@Builder
public record UserWithTokenResponse(
        UserProfileResponse user,
        AccessToken accessToken
) {
}

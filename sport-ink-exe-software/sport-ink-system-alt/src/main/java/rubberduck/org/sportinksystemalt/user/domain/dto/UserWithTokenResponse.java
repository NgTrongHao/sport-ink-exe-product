package rubberduck.org.sportinksystemalt.user.domain.dto;

import lombok.Builder;
import rubberduck.org.sportinksystemalt.shared.domain.AccessToken;
import rubberduck.org.sportinksystemalt.user.domain.entity.User;

@Builder
public record UserWithTokenResponse(
        User user,
        AccessToken accessToken
) {
}

package rubberduck.org.sportinksystemalt.user.domain.dto;

import lombok.Builder;
import rubberduck.org.sportinksystemalt.shared.domain.AccessToken;
import rubberduck.org.sportinksystemalt.user.domain.entity.User;

@Builder
public record LoginUserResponse(
        User user,
        AccessToken accessToken
) {
}

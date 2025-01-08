package rubberduck.org.sportinksystem.core.applicationservice.user.model;

import rubberduck.org.sportinksystem.core.domain.entity.token.AccessToken;

public record RegisterResponse(
        AccessToken accessToken,
        UserInfo user
) {
}

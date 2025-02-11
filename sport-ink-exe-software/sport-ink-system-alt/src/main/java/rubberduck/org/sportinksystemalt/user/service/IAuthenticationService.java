package rubberduck.org.sportinksystemalt.user.service;

import rubberduck.org.sportinksystemalt.user.domain.dto.RegisterUserRequest;
import rubberduck.org.sportinksystemalt.user.domain.dto.UserWithTokenResponse;

public interface IAuthenticationService {
    UserWithTokenResponse register(RegisterUserRequest request);
}

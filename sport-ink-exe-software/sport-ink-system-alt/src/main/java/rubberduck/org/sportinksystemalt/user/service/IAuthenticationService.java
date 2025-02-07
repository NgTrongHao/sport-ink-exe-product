package rubberduck.org.sportinksystemalt.user.service;

import rubberduck.org.sportinksystemalt.user.domain.dto.RegisterUserRequest;
import rubberduck.org.sportinksystemalt.user.domain.dto.RegisterUserResponse;

public interface IAuthenticationService {
    RegisterUserResponse register(RegisterUserRequest request);
}

package rubberduck.org.sportinksystem.core.applicationservice.user.usecase.create;

import org.springframework.stereotype.Service;
import rubberduck.org.sportinksystem.core.applicationservice.cache.TokenCacheService;
import rubberduck.org.sportinksystem.core.applicationservice.hashing.PasswordHasher;
import rubberduck.org.sportinksystem.core.applicationservice.mail.MailSender;
import rubberduck.org.sportinksystem.core.applicationservice.token.TokenProvider;
import rubberduck.org.sportinksystem.core.applicationservice.user.model.RegisterRequest;
import rubberduck.org.sportinksystem.core.applicationservice.user.model.RegisterResponse;
import rubberduck.org.sportinksystem.core.applicationservice.user.model.UserInfo;
import rubberduck.org.sportinksystem.core.applicationservice.user.repository.UserRepository;
import rubberduck.org.sportinksystem.core.domain.entity.token.AccessToken;
import rubberduck.org.sportinksystem.core.domain.entity.user.User;
import rubberduck.org.sportinksystem.core.domain.exception.UserAlreadyExistsException;

import java.util.HashMap;

@Service
public class RegisterInteractor implements RegisterUsecase {
    private final UserRepository userRepository;
    private final PasswordHasher passwordHasher;
    private final MailSender mailSender;
    private final TokenProvider tokenProvider;
    private final TokenCacheService tokenCacheService;

    public RegisterInteractor(UserRepository userRepository, PasswordHasher passwordHasher, MailSender mailSender, TokenProvider tokenProvider, TokenCacheService tokenCacheService) {
        this.userRepository = userRepository;
        this.passwordHasher = passwordHasher;
        this.mailSender = mailSender;
        this.tokenProvider = tokenProvider;
        this.tokenCacheService = tokenCacheService;
    }

    @Override
    public RegisterResponse execute(RegisterRequest request) {
        if (checkIfUserExists(request)) {
            throw new UserAlreadyExistsException("User already exists");
        }

        User user = User.createNewUser(
                request.email(),
                request.username(),
                request.firstName(),
                request.middleName(),
                request.lastName(),
                passwordHasher.hash(request.password())
        );

        User savedUser = userRepository.save(user);

        mailSender.sendWelcomeEmail(user.getEmail().email(), user.getFullName().getFullName());

        AccessToken accessToken = generateToken(savedUser);
        tokenCacheService.addAccessToken(
                savedUser.getUsername().username(),
                accessToken.getToken(),
                tokenProvider.getAccessTokenExpiration()
        );

        return new RegisterResponse(
                generateToken(savedUser),
                UserInfo.userInfoFromUser(savedUser)
        );
    }

    private boolean checkIfUserExists(RegisterRequest request) {
        return existsByEmail(request.email()) || existsByUsername(request.username());
    }

    private boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    private boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    private AccessToken generateToken(User user) {
        return tokenProvider.generateAccessToken(
                new HashMap<>() {{
                    put("userId", user.getId());
                    put("username", user.getUsername().username());
                    put("roles", user.getRoles());
                }},
                user.getUsername().username()
        );
    }
}

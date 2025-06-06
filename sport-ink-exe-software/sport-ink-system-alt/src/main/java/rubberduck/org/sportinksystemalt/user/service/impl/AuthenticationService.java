package rubberduck.org.sportinksystemalt.user.service.impl;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import rubberduck.org.sportinksystemalt.shared.common.util.RandomStringUtil;
import rubberduck.org.sportinksystemalt.shared.domain.AccessToken;
import rubberduck.org.sportinksystemalt.shared.service.mail.MailSender;
import rubberduck.org.sportinksystemalt.shared.service.token.TokenProvider;
import rubberduck.org.sportinksystemalt.user.domain.dto.LoginUserRequest;
import rubberduck.org.sportinksystemalt.user.domain.dto.LoginUserResponse;
import rubberduck.org.sportinksystemalt.user.domain.dto.RegisterUserRequest;
import rubberduck.org.sportinksystemalt.user.domain.dto.UserWithTokenResponse;
import rubberduck.org.sportinksystemalt.user.domain.entity.Role;
import rubberduck.org.sportinksystemalt.user.domain.entity.User;
import rubberduck.org.sportinksystemalt.user.domain.mapper.UserMapper;
import rubberduck.org.sportinksystemalt.user.repository.UserRepository;
import rubberduck.org.sportinksystemalt.user.service.IAuthenticationService;

import java.util.HashMap;
import java.util.Set;

@Service
public class AuthenticationService implements IAuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final MailSender mailSender;
    private final TokenProvider tokenProvider;

    public AuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder, MailSender mailSender, TokenProvider tokenProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.mailSender = mailSender;
        this.tokenProvider = tokenProvider;
    }

    @Override
    public UserWithTokenResponse register(RegisterUserRequest request) {
        if (checkIfUserExists(request)) {
            throw new IllegalArgumentException("User with this email or username already exists");
        }

        User savedUser = saveRegisteredUser(request);
        AccessToken accessToken = generateAccessToken(savedUser);
        sendWelcomeEmail(savedUser);
        sendEmailVerificationEmail(savedUser, accessToken.getToken());
        tokenProvider.cacheAccessToken(accessToken);

        return buildRegisterUserResponse(savedUser, accessToken);
    }

    private User saveRegisteredUser(RegisterUserRequest request) {
        return saveUser(
                User.builder()
                        .email(request.email())
                        .username(request.username())
                        .firstName(request.firstName())
                        .middleName(request.middleName())
                        .lastName(request.lastName())
                        .password(passwordEncoder.encode(request.password()))
                        .roles(Set.of(Role.REGISTRATION_USER))
                        .build()
        );

    }

    private User saveUser(User user) {
        return userRepository.save(user);
    }

    private AccessToken generateAccessToken(User user) {
        return tokenProvider.generateAccessToken(
                new HashMap<>() {{
                    put("userId", user.getUserId());
                    put("username", user.getUsername());
                    put("roles", user.getRoles());
                }},
                user.getUsername()
        );
    }

    private boolean checkIfUserExists(RegisterUserRequest request) {
        return existsByEmail(request.email()) || existsByUsername(request.username());
    }

    private boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    private boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    private void sendWelcomeEmail(User user) {
        mailSender.sendWelcomeEmail(user.getEmail(), String.format("Welcome, %s!",
                String.join(" ", user.getFirstName(), user.getMiddleName(), user.getLastName())
        ));
    }

    private void sendEmailVerificationEmail(User user, String token) {
        mailSender.sendEmailVerificationEmail(user.getEmail(), String.format("Hello, %s!",
                String.join(" ", user.getFirstName(), user.getMiddleName(), user.getLastName())
        ), token);
    }

    private UserWithTokenResponse buildRegisterUserResponse(User user, AccessToken accessToken) {
        return UserWithTokenResponse.builder()
                .accessToken(accessToken)
                .user(UserMapper.mapToUserProfileResponse(user))
                .build();
    }

    @Override
    public LoginUserResponse login(LoginUserRequest request) {
        User user = userRepository.findByUsername(request.username()).orElseThrow(() -> new IllegalArgumentException("Invalid username or password"));

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new IllegalArgumentException("Invalid username or password");
        }

        AccessToken accessToken = generateAccessToken(user);

        tokenProvider.cacheAccessToken(accessToken);

        return buildLoginUserResponse(user, accessToken);
    }

    private LoginUserResponse buildLoginUserResponse(User user, AccessToken accessToken) {
        return LoginUserResponse.builder()
                .accessToken(accessToken)
                .user(user)
                .build();
    }

    @Override
    public UserWithTokenResponse loginWithGoogle(String token) {
        try {
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(token);
            String email = decodedToken.getEmail();
            String username = email.split("@")[0];
            String firstName = decodedToken.getName();
            String profilePicture = decodedToken.getPicture();

            User user = userRepository.findByEmail(email).orElseGet(() -> saveUser(
                    User.builder()
                            .email(email)
                            .username(username)
                            .password(passwordEncoder.encode(RandomStringUtil.randomAlphaNumericAndSpecialCharacter(6)))
                            .firstName(firstName)
                            .profilePicture(profilePicture)
                            .roles(Set.of(Role.REGISTRATION_USER))
                            .build()
            ));

            AccessToken accessToken = generateAccessToken(user);
            tokenProvider.cacheAccessToken(accessToken);

            return UserWithTokenResponse.builder()
                    .accessToken(accessToken)
                    .user(UserMapper.mapToUserProfileResponse(user))
                    .build();
        } catch (FirebaseAuthException e) {
            throw new RuntimeException(e);
        }
    }

}

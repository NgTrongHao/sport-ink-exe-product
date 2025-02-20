package rubberduck.org.sportinksystemalt.user.service.impl;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rubberduck.org.sportinksystemalt.shared.domain.AccessToken;
import rubberduck.org.sportinksystemalt.shared.exception.handler.ResourceNotFoundException;
import rubberduck.org.sportinksystemalt.shared.service.token.TokenProvider;
import rubberduck.org.sportinksystemalt.user.domain.dto.*;
import rubberduck.org.sportinksystemalt.user.domain.entity.*;
import rubberduck.org.sportinksystemalt.user.domain.mapper.UserMapper;
import rubberduck.org.sportinksystemalt.user.repository.PlayerRepository;
import rubberduck.org.sportinksystemalt.user.repository.UserRepository;
import rubberduck.org.sportinksystemalt.user.repository.VenueOwnerRepository;
import rubberduck.org.sportinksystemalt.user.service.IUserService;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;

import static rubberduck.org.sportinksystemalt.user.domain.mapper.UserMapper.mapToUserProfileResponse;

@Service
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final PlayerRepository playerRepository;
    private final VenueOwnerRepository venueOwnerRepository;
    private final TokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PlayerRepository playerRepository, VenueOwnerRepository venueOwnerRepository, TokenProvider tokenProvider, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.playerRepository = playerRepository;
        this.venueOwnerRepository = venueOwnerRepository;
        this.tokenProvider = tokenProvider;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    @Cacheable(value = "user", key = "#username")
    public UserProfileResponse updateUserProfile(String username, UpdateUserProfileRequest request) {
        User user = findUserByUsername(username);
        updateUserFields(user, request);
        userRepository.save(user);
        return mapToUserProfileResponse(user);
    }

    private void updateUserFields(User user, UpdateUserProfileRequest request) {
        updateIfNotNull(user::setEmail, request.getEmail(), this::validateEmail);
        updateIfNotNull(user::setFirstName, request.getFirstName());
        updateIfNotNull(user::setMiddleName, request.getMiddleName());
        updateIfNotNull(user::setLastName, request.getLastName());
        updateIfNotNull(user::setPassword, hashPassword(request.getPassword()));
        updateIfNotNull(user::setPhoneNumber, request.getPhoneNumber(), this::validatePhoneNumber);
        updateIfNotNull(user::setProfilePicture, request.getProfilePicture());
        updateIfNotNull(user::setCoverPicture, request.getCoverPicture());
        updateIfNotNull(user::setBio, request.getBio());
    }

    private <T> void updateIfNotNull(Consumer<T> setter, T value) {
        if (value != null) {
            setter.accept(value);
        }
    }

    private <T> void updateIfNotNull(Consumer<T> setter, T value, Consumer<T> validator) {
        if (value != null) {
            validator.accept(value);
            setter.accept(value);
        }
    }

    private void validateEmail(String email) {
        if (!isValidEmail(email)) {
            throw new IllegalArgumentException("Invalid email format");
        }
    }

    private boolean isValidEmail(String email) {
        return email != null && email.matches("[^@]+@[^@]+\\.[^@]+");
    }

    private void validatePhoneNumber(String phoneNumber) {
        if (!isValidPhoneNumber(phoneNumber)) {
            throw new IllegalArgumentException("Invalid phone number format");
        }
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber != null && phoneNumber.matches("\\d{10}");
    }

    private String hashPassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    @Override
    @Transactional
    public UserWithTokenResponse createPlayerProfile(String username, CreatePlayerProfileRequest request) {
        User user = findUserByUsername(username);
        validateRegistrationUserState(user);
        updateUserProfile(user, request.profilePicture(), request.coverPicture(), request.bio(), Role.PLAYER);
        createOrUpdatePlayer(user, request);

        return generateUserWithTokenResponse(user);
    }

    @Override
    @Transactional
    public UserWithTokenResponse createVenueOwnerProfile(String username, CreateVenueOwnerProfileRequest request) {
        User user = findUserByUsername(username);
        validateRegistrationUserState(user);
        updateUserProfile(user, request.profilePicture(), request.coverPicture(), request.bio(), Role.VENUE_OWNER);
        createOrUpdateVenueOwner(user, request);

        return generateUserWithTokenResponse(user);
    }

    @Override
    public VenueOwner getVenueOwnerById(UUID id) {
        return venueOwnerRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Venue owner not found"));
    }

    @Override
    @Cacheable(value = "user", key = "#username")
    public UserProfileResponse getUserProfile(String username) {
        User user = findUserByUsername(username);
        return mapToUserProfileResponse(user);
    }

    @Override
    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));
    }

    private void validateRegistrationUserState(User user) {
        if (!user.getRoles().contains(Role.REGISTRATION_USER)) {
            throw new IllegalStateException("User is not a registration user");
        }
    }

    private void updateUserProfile(User user, String profilePicture, String coverPicture, String bio, Role role) {
        user.setProfilePicture(profilePicture);
        user.setCoverPicture(coverPicture);
        user.setBio(bio);
        user.setRoles(new HashSet<>(Set.of(role)));
        userRepository.save(user);
    }

    private void createOrUpdatePlayer(User user, CreatePlayerProfileRequest request) {
        Player player = user.getPlayer();
        if (player == null) {
            player = new Player();
            player.setUser(user);
            user.setPlayer(player);
        }
        player.setGender(Gender.valueOf(request.gender().toUpperCase()));
        player.setPreferredSport(request.referenceSport());
        playerRepository.save(player);

        user.setPlayer(player);
    }

    private void createOrUpdateVenueOwner(User user, CreateVenueOwnerProfileRequest request) {
        VenueOwner venueOwner = user.getVenueOwner();
        if (venueOwner == null) {
            venueOwner = new VenueOwner();
            venueOwner.setUser(user);
            user.setVenueOwner(venueOwner);
        }
        venueOwner.setTaxNumber(request.taxCode());
        venueOwnerRepository.save(venueOwner);

        user.setVenueOwner(venueOwner);
    }

    private AccessToken generateAccessToken(User user) {
        AccessToken accessToken = tokenProvider.generateAccessToken(
                new HashMap<>() {{
                    put("userId", user.getUserId());
                    put("username", user.getUsername());
                    put("roles", user.getRoles());
                }},
                user.getUsername()
        );
        invalidateToken(user.getUsername());
        tokenProvider.cacheAccessToken(accessToken);
        return accessToken;
    }

    private void invalidateToken(String username) {
        tokenProvider.invalidateAccessToken(username);
    }

    private UserWithTokenResponse generateUserWithTokenResponse(User user) {
        return new UserWithTokenResponse(UserMapper.mapToUserProfileResponse(user), generateAccessToken(user));
    }
}

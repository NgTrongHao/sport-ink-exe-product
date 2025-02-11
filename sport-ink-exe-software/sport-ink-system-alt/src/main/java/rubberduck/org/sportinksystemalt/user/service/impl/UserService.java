package rubberduck.org.sportinksystemalt.user.service.impl;

import org.springframework.stereotype.Service;
import rubberduck.org.sportinksystemalt.shared.domain.AccessToken;
import rubberduck.org.sportinksystemalt.shared.exception.handler.ResourceNotFoundException;
import rubberduck.org.sportinksystemalt.shared.service.cache.CacheService;
import rubberduck.org.sportinksystemalt.shared.service.token.TokenProvider;
import rubberduck.org.sportinksystemalt.user.domain.dto.CreatePlayerProfileRequest;
import rubberduck.org.sportinksystemalt.user.domain.dto.CreateVenueOwnerProfileRequest;
import rubberduck.org.sportinksystemalt.user.domain.dto.UserWithTokenResponse;
import rubberduck.org.sportinksystemalt.user.domain.entity.*;
import rubberduck.org.sportinksystemalt.user.repository.PlayerRepository;
import rubberduck.org.sportinksystemalt.user.repository.UserRepository;
import rubberduck.org.sportinksystemalt.user.repository.VenueOwnerRepository;
import rubberduck.org.sportinksystemalt.user.service.IUserService;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@Service
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final PlayerRepository playerRepository;
    private final VenueOwnerRepository venueOwnerRepository;
    private final TokenProvider tokenProvider;
    private final CacheService cacheService;

    public UserService(UserRepository userRepository, PlayerRepository playerRepository, VenueOwnerRepository venueOwnerRepository, TokenProvider tokenProvider, CacheService cacheService) {
        this.userRepository = userRepository;
        this.playerRepository = playerRepository;
        this.venueOwnerRepository = venueOwnerRepository;
        this.tokenProvider = tokenProvider;
        this.cacheService = cacheService;
    }

    @Override
    public UserWithTokenResponse createPlayerProfile(String username, CreatePlayerProfileRequest request) {
        User user = findUserByUsername(username);
        validateRegistrationUserState(user);
        updateUserProfile(user, request.profilePicture(), request.coverPicture(), request.bio(), Role.PLAYER);
        createOrUpdatePlayer(user, request);

        return createUserWithTokenResponse(user);
    }

    @Override
    public UserWithTokenResponse createVenueOwnerProfile(String username, CreateVenueOwnerProfileRequest request) {
        User user = findUserByUsername(username);
        validateRegistrationUserState(user);
        updateUserProfile(user, request.profilePicture(), request.coverPicture(), request.bio(), Role.VENUE_OWNER);
        createOrUpdateVenueOwner(user, request);

        return createUserWithTokenResponse(user);
    }

    private User findUserByUsername(String username) {
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
        player.setGender(Gender.valueOf(request.gender()));
        player.setPreferredSport(request.referenceSport());
        playerRepository.save(player);

        user.setPlayer(player);
        cacheUser(user);
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
        cacheUser(user);
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

    private UserWithTokenResponse createUserWithTokenResponse(User user) {
        return new UserWithTokenResponse(user, generateAccessToken(user));
    }

    private void cacheUser(User user) {
        cacheService.put(user.getUsername(), user, 3600);
    }
}

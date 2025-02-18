package rubberduck.org.sportinksystemalt.user.service;

import rubberduck.org.sportinksystemalt.user.domain.dto.*;
import rubberduck.org.sportinksystemalt.user.domain.entity.User;
import rubberduck.org.sportinksystemalt.user.domain.entity.VenueOwner;

import java.util.UUID;

public interface IUserService {
    UserWithTokenResponse createPlayerProfile(String username, CreatePlayerProfileRequest request);

    UserWithTokenResponse createVenueOwnerProfile(String username, CreateVenueOwnerProfileRequest request);

    UserProfileResponse updateUserProfile(String username, UpdateUserProfileRequest request);

    VenueOwner getVenueOwnerById(UUID id);

    UserProfileResponse getUserProfile(String username);

    User findUserByUsername(String username);
}

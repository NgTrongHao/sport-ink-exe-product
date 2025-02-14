package rubberduck.org.sportinksystemalt.user.service;

import rubberduck.org.sportinksystemalt.user.domain.dto.CreatePlayerProfileRequest;
import rubberduck.org.sportinksystemalt.user.domain.dto.CreateVenueOwnerProfileRequest;
import rubberduck.org.sportinksystemalt.user.domain.dto.UpdateUserProfileRequest;
import rubberduck.org.sportinksystemalt.user.domain.dto.UserWithTokenResponse;
import rubberduck.org.sportinksystemalt.user.domain.entity.VenueOwner;

import java.util.UUID;

public interface IUserService {
    UserWithTokenResponse createPlayerProfile(String username, CreatePlayerProfileRequest request);

    UserWithTokenResponse createVenueOwnerProfile(String username, CreateVenueOwnerProfileRequest request);

    UserWithTokenResponse updateUserProfile(String username, UpdateUserProfileRequest request);

    VenueOwner getVenueOwnerById(UUID id);
}

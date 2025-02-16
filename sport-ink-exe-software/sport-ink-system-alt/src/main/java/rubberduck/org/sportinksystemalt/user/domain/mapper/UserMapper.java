package rubberduck.org.sportinksystemalt.user.domain.mapper;

import rubberduck.org.sportinksystemalt.user.domain.dto.PlayerProfileResponse;
import rubberduck.org.sportinksystemalt.user.domain.dto.UserProfileResponse;
import rubberduck.org.sportinksystemalt.user.domain.dto.VenueOwnerProfileResponse;
import rubberduck.org.sportinksystemalt.user.domain.entity.Player;
import rubberduck.org.sportinksystemalt.user.domain.entity.Role;
import rubberduck.org.sportinksystemalt.user.domain.entity.User;
import rubberduck.org.sportinksystemalt.user.domain.entity.VenueOwner;

import java.util.stream.Collectors;

public class UserMapper {

    public static UserProfileResponse mapToUserProfileResponse(User user) {
        return UserProfileResponse.builder()
                .userId(user.getUserId())
                .username(user.getUsername())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .middleName(user.getMiddleName())
                .lastName(user.getLastName())
                .phoneNumber(user.getPhoneNumber())
                .profilePicture(user.getProfilePicture())
                .coverPicture(user.getCoverPicture())
                .bio(user.getBio())
                .roles(user.getRoles().stream().map(Role::name).collect(Collectors.toSet()))
                .enabled(user.isEnabled())
                .emailVerified(user.isVerified())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .playerProfile(mapToPlayerProfileResponse(user.getPlayer()))
                .venueOwnerProfile(mapToVenueOwnerProfileResponse(user.getVenueOwner()))
                .build();
    }

    private static PlayerProfileResponse mapToPlayerProfileResponse(Player player) {
        if (player == null) {
            return null;
        }
        return PlayerProfileResponse.builder()
                .gender(player.getGender().name())
                .referenceSport(player.getPreferredSport())
                .build();
    }

    private static VenueOwnerProfileResponse mapToVenueOwnerProfileResponse(VenueOwner venueOwner) {
        if (venueOwner == null) {
            return null;
        }
        return VenueOwnerProfileResponse.builder()
                .taxCode(venueOwner.getTaxNumber())
                .build();
    }
}

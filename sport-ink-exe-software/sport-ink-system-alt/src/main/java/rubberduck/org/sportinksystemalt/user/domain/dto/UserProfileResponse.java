package rubberduck.org.sportinksystemalt.user.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public record UserProfileResponse(
        UUID userId,
        String email,
        String username,
        String firstName,
        String middleName,
        String lastName,
        String phoneNumber,
        String profilePicture,
        String coverPicture,
        String bio,
        Set<String> roles,
        boolean enabled,
        boolean emailVerified,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,

        PlayerProfileResponse playerProfile,
        VenueOwnerProfileResponse venueOwnerProfile
) implements Serializable {
}
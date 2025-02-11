package rubberduck.org.sportinksystemalt.user.domain.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

public record CreateVenueOwnerProfileRequest(
        String profilePicture,

        String coverPicture,

        String bio,

        @NotEmpty(message = "Phone number is required")
        @Pattern(regexp = "^[0-9]{10}$")
        String phoneNumber,

        @Pattern(regexp = "^[0-9]{10}$")
        String taxCode
) {
}

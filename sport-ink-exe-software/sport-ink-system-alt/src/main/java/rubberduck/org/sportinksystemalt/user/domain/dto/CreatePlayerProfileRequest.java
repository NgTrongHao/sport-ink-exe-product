package rubberduck.org.sportinksystemalt.user.domain.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

public record CreatePlayerProfileRequest(
        String profilePicture,

        String coverPicture,

        String bio,

        @NotEmpty(message = "Phone number is required")
        @Pattern(regexp = "^[0-9]{10}$")
        String phoneNumber,

        @NotEmpty(message = "Gender is required")
        @Pattern(regexp = "^(male|female|other)$")
        String gender,

        String referenceSport
) {
}

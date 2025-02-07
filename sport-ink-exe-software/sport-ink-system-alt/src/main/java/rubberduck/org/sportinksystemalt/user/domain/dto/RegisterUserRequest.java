package rubberduck.org.sportinksystemalt.user.domain.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

@Builder
public record RegisterUserRequest(
        @Email
        String email,

        @NotBlank(message = "Username is required")
        @Pattern(regexp = "^[a-zA-Z0-9_]{3,20}$", message = "Username must be between 3 and 20 characters and can only contain letters, numbers, and underscores")
        String username,

        String password,

        @NotEmpty(message = "First name is required")
        String firstName,

        String middleName,

        @NotEmpty(message = "Last name is required")
        String lastName
) {
}

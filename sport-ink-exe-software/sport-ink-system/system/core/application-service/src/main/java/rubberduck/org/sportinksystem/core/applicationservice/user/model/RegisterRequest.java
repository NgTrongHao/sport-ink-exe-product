package rubberduck.org.sportinksystem.core.applicationservice.user.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public record RegisterRequest(
        @Email
        String email,

        @NotBlank
        String username,

        @NotBlank
        String password,

        @NotEmpty
        String firstName,

        String middleName,

        @NotEmpty
        String lastName
) {
}

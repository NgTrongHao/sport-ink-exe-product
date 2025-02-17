package rubberduck.org.sportinksystemalt.user.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

@Builder
public record LoginUserRequest(

        @NotBlank(message = "Username is required")
        @Pattern(regexp = "^[a-zA-Z0-9_]{3,20}$", message = "Username must be between 3 and 20 characters and can only contain letters, numbers, and underscores")
        String username,

        @NotBlank(message = "Password is required")
        String password


) {
}

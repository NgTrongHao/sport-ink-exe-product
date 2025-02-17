package rubberduck.org.sportinksystemalt.administration.domain.dto;

import jakarta.validation.constraints.NotEmpty;

public record CreateSportRequest(
        @NotEmpty
        String name,

        String description,
        String iconUrl
) {
}

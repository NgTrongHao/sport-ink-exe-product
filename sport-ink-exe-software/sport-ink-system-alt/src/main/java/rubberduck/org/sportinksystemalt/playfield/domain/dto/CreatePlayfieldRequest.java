package rubberduck.org.sportinksystemalt.playfield.domain.dto;

import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public record CreatePlayfieldRequest(
        String name,
        String description,
        List<String> images,
        UUID venueOwnerId,

        @NotNull
        UUID venueId,

        @NotNull
        List<UUID> sportIds
) {
}

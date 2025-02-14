package rubberduck.org.sportinksystemalt.playfield.domain.dto;

import rubberduck.org.sportinksystemalt.playfield.domain.entity.PlayfieldSport;

import java.util.List;
import java.util.UUID;

public record PlayfieldResponse(
        UUID id,
        String name,
        String description,
        List<String> imageUrls,
        boolean enabled,
        List<PlayfieldSportResponse> playfieldSportList
) {
}

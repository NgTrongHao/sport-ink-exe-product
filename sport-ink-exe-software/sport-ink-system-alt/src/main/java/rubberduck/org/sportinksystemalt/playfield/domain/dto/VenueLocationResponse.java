package rubberduck.org.sportinksystemalt.playfield.domain.dto;

import java.util.List;
import java.util.UUID;

public record VenueLocationResponse(
        UUID id,
        String address,
        double latitude,
        double longitude,
        String description,
        List<String> imageUrls,
        String phoneContact,
        List<OpeningHoursDTO> openingHours
) {
}

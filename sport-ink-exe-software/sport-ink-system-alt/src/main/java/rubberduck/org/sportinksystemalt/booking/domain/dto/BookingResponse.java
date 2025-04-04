package rubberduck.org.sportinksystemalt.booking.domain.dto;

import rubberduck.org.sportinksystemalt.administration.domain.dto.SportResponse;
import rubberduck.org.sportinksystemalt.booking.domain.entity.BookingStatus;
import rubberduck.org.sportinksystemalt.playfield.domain.dto.PlayfieldResponse;
import rubberduck.org.sportinksystemalt.playfield.domain.dto.VenueLocationResponse;

import java.time.LocalDateTime;
import java.util.UUID;

public record BookingResponse(
        UUID bookingId,
        LocalDateTime startTime,
        LocalDateTime endTime,
        Double price,
        PlayfieldResponse playfield,
        VenueLocationResponse venueLocation,
        String bookingCode,
        BookingStatus status,
        String bookingNote,
        SportResponse sport
) {
}

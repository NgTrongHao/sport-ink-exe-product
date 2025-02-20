package rubberduck.org.sportinksystemalt.booking.domain.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public record CreateBookingRequest(

        @NotEmpty
        UUID playfieldId,

        @NotEmpty
        UUID sportId,

        @NotEmpty
        LocalDate bookingDate,

        @NotEmpty
        LocalTime startTime,

        @NotEmpty
        @Pattern(regexp = "^(30|60|90|120|150|180|210|240|270|300)$")
        int duration,

        String bookingNote
) {
}

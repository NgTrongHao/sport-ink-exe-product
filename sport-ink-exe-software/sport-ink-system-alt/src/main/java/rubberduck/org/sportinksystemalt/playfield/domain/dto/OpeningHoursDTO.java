package rubberduck.org.sportinksystemalt.playfield.domain.dto;

import java.time.DayOfWeek;
import java.time.LocalTime;

public record OpeningHoursDTO(
        DayOfWeek dayOfWeek,
        LocalTime openingTime,
        LocalTime closingTime
) {
}

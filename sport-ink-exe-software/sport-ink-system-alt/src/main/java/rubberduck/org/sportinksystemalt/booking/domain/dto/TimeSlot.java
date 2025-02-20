package rubberduck.org.sportinksystemalt.booking.domain.dto;

import lombok.Builder;

import java.time.LocalTime;

@Builder
public record TimeSlot(
        LocalTime startTime,
        LocalTime endTime
) {
}


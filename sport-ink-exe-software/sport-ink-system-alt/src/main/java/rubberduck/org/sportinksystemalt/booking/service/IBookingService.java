package rubberduck.org.sportinksystemalt.booking.service;

import rubberduck.org.sportinksystemalt.booking.domain.dto.CreateBookingRequest;
import rubberduck.org.sportinksystemalt.booking.domain.dto.TimeSlot;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface IBookingService {
    void createBooking(String username, CreateBookingRequest request);

    List<TimeSlot> getBookingsByPlayfieldId(UUID playfieldId, LocalDate date);
}

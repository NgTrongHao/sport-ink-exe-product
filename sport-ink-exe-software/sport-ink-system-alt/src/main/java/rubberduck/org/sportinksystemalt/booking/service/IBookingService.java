package rubberduck.org.sportinksystemalt.booking.service;

import org.springframework.data.domain.Page;
import rubberduck.org.sportinksystemalt.booking.domain.dto.BookingResponse;
import rubberduck.org.sportinksystemalt.booking.domain.dto.CreateBookingRequest;
import rubberduck.org.sportinksystemalt.booking.domain.dto.TimeSlot;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface IBookingService {
    void createBooking(String username, CreateBookingRequest request);

    List<TimeSlot> getBookingsByPlayfieldId(UUID playfieldId, LocalDate date);

    Page<BookingResponse> getAllBookingsByPlayfieldId(UUID playfieldId, int page, int size);

    Page<BookingResponse> getAllBookings(int page, int size);
}

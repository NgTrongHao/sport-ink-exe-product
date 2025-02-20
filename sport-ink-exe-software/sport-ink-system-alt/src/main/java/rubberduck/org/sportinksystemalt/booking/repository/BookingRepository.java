package rubberduck.org.sportinksystemalt.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import rubberduck.org.sportinksystemalt.booking.domain.entity.Booking;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface BookingRepository extends JpaRepository<Booking, UUID> {

    @Query("SELECT CASE WHEN COUNT(b) > 0 THEN TRUE ELSE FALSE END FROM Booking b WHERE b.bookingPlayfield.id = :playfieldId AND b.bookingDate = :bookingDate AND b.startDateTime < :endTime AND b.endDateTime > :startTime")
    boolean existsOverlappingBooking(UUID playfieldId, LocalDate bookingDate, LocalDateTime startTime, LocalDateTime endTime);

    List<Booking> findByBookingPlayfield_IdAndBookingDate(UUID bookingPlayfieldId, LocalDate bookingDate);
}
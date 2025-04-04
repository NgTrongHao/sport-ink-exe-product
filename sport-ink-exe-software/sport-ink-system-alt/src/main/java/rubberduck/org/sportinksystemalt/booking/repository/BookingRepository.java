package rubberduck.org.sportinksystemalt.booking.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rubberduck.org.sportinksystemalt.booking.domain.entity.Booking;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface BookingRepository extends JpaRepository<Booking, UUID> {

    @Query("SELECT CASE WHEN COUNT(b) > 0 THEN TRUE ELSE FALSE END FROM Booking b WHERE b.bookingPlayfield.id = :playfieldId AND b.bookingDate = :bookingDate AND b.startDateTime < :endTime AND b.endDateTime > :startTime")
    boolean existsOverlappingBooking(UUID playfieldId, LocalDate bookingDate, LocalDateTime startTime, LocalDateTime endTime);

    List<Booking> findByBookingPlayfield_IdAndBookingDate(UUID bookingPlayfieldId, LocalDate bookingDate);

    long countBookingsByBookingDateBetween(LocalDate bookingDateAfter, LocalDate bookingDateBefore);

    @Query("SELECT b.bookingDate AS day, COUNT(b) AS bookingCount " +
            "FROM Booking b " +
            "WHERE b.bookingDate BETWEEN :startDate AND :endDate " +
            "GROUP BY b.bookingDate " +
            "ORDER BY b.bookingDate")
    Map<LocalDate, Long> countBookingsPerDay(@Param("startDate") LocalDate startDate,
                                             @Param("endDate") LocalDate endDate);

    @Query("SELECT vl.address AS venue, COUNT(b) AS bookingCount " +
            "FROM Booking b " +
            "JOIN b.bookingPlayfield p " +
            "JOIN p.venueLocation vl " +
            "WHERE b.bookingDate BETWEEN :startDate AND :endDate " +
            "GROUP BY vl.address " +
            "ORDER BY bookingCount DESC")
    Map<String, Long> countBookingsPerVenue(@Param("startDate") LocalDate startDate,
                                            @Param("endDate") LocalDate endDate);

    Page<Booking> findAllByBookingPlayfield_Id(UUID playfieldId, Pageable pageable);
}
package rubberduck.org.sportinksystemalt.booking.service.impl;

import org.springframework.stereotype.Component;
import rubberduck.org.sportinksystemalt.booking.domain.dto.CreateBookingRequest;
import rubberduck.org.sportinksystemalt.booking.repository.BookingRepository;
import rubberduck.org.sportinksystemalt.playfield.domain.entity.Playfield;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;

@Component
public class BookingValidator {
    private final BookingRepository bookingRepository;

    public BookingValidator(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    public void validate(Playfield playfield, CreateBookingRequest request) {
        List<Supplier<Boolean>> validations = List.of(
                () -> isPlayfieldEnabled(playfield, request),
                () -> isWithinOpeningHours(playfield, request),
                () -> supportsRequestedSport(playfield, request.sportId()),
                () -> !isBookingOverlapping(request)
        );

        List<String> errorMessages = List.of(
                "Playfield for this sport is not enabled",
                "Playfield is not available during the requested time",
                "Playfield does not support the requested sport",
                "Booking overlaps with an existing booking"
        );

        for (int i = 0; i < validations.size(); i++) {
            if (!validations.get(i).get()) {
                throw new IllegalArgumentException(errorMessages.get(i));
            }
        }
    }

    private boolean isPlayfieldEnabled(Playfield playfield, CreateBookingRequest request) {
        return playfield.getVenueLocation().isEnabled() && playfield.getPlayfieldSportList().stream()
                .anyMatch(sport -> sport.getSport().getId().equals(request.sportId()));
    }

    private boolean isWithinOpeningHours(Playfield playfield, CreateBookingRequest request) {
        return playfield.getVenueLocation().getOpeningHoursList().stream()
                .anyMatch(hours -> hours.getDayOfWeek().equals(request.bookingDate().getDayOfWeek())
                        && !hours.getOpeningTime().isAfter(request.startTime())
                        && !hours.getClosingTime().isBefore(request.startTime().plusMinutes(request.duration())));
    }

    private boolean supportsRequestedSport(Playfield playfield, UUID sportId) {
        return playfield.getPlayfieldSportList().stream()
                .anyMatch(sport -> sport.getSport().getId().equals(sportId));
    }

    private boolean isBookingOverlapping(CreateBookingRequest request) {
        return bookingRepository.existsOverlappingBooking(
                request.playfieldId(),
                request.bookingDate(),
                LocalDateTime.of(request.bookingDate(), request.startTime()),
                LocalDateTime.of(request.bookingDate(), request.startTime().plusMinutes(request.duration()))
        );
    }
}

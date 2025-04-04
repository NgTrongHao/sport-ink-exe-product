package rubberduck.org.sportinksystemalt.booking.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import rubberduck.org.sportinksystemalt.administration.service.ISportService;
import rubberduck.org.sportinksystemalt.booking.domain.dto.BookingResponse;
import rubberduck.org.sportinksystemalt.booking.domain.dto.CreateBookingRequest;
import rubberduck.org.sportinksystemalt.booking.domain.dto.TimeSlot;
import rubberduck.org.sportinksystemalt.booking.domain.entity.Booking;
import rubberduck.org.sportinksystemalt.booking.domain.entity.BookingStatus;
import rubberduck.org.sportinksystemalt.booking.domain.mapper.BookingMapper;
import rubberduck.org.sportinksystemalt.booking.repository.BookingRepository;
import rubberduck.org.sportinksystemalt.booking.service.IBookingService;
import rubberduck.org.sportinksystemalt.playfield.domain.entity.Playfield;
import rubberduck.org.sportinksystemalt.playfield.domain.entity.PlayfieldSport;
import rubberduck.org.sportinksystemalt.playfield.service.IPlayfieldService;
import rubberduck.org.sportinksystemalt.user.domain.entity.Role;
import rubberduck.org.sportinksystemalt.user.domain.entity.User;
import rubberduck.org.sportinksystemalt.user.service.IUserService;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class BookingService implements IBookingService {
    private final BookingRepository bookingRepository;
    private final IPlayfieldService playfieldService;
    private final ISportService sportService;
    private final IUserService userService;
    private final BookingValidator bookingValidator;
    private final BookingPriceCalculator bookingPriceCalculator;

    public BookingService(BookingRepository bookingRepository, IPlayfieldService playfieldService, ISportService sportService, IUserService userService, BookingValidator bookingValidator, BookingPriceCalculator bookingPriceCalculator) {
        this.bookingRepository = bookingRepository;
        this.playfieldService = playfieldService;
        this.sportService = sportService;
        this.userService = userService;
        this.bookingValidator = bookingValidator;
        this.bookingPriceCalculator = bookingPriceCalculator;
    }

    @Override
    public void createBooking(String username, CreateBookingRequest request) {
        User user = userService.findUserByUsername(username);
        Playfield playfield = playfieldService.findPlayfieldById(request.playfieldId());

        bookingValidator.validate(playfield, request);

        Double bookingPrice = bookingPriceCalculator.calculate(getMatchingPlayfieldSport(playfield, request.sportId()), request.startTime(), request.duration(), request.bookingDate());

        Booking booking = buildBooking(user, playfield, request, bookingPrice);
        bookingRepository.save(booking);
    }

    @Override
    public List<TimeSlot> getBookingsByPlayfieldId(UUID playfieldId, LocalDate date) {
        return bookingRepository.findByBookingPlayfield_IdAndBookingDate(playfieldId, date).stream()
                .map(booking -> TimeSlot.builder()
                        .startTime(booking.getBookingTime())
                        .endTime(booking.getBookingTime().plusMinutes(booking.getBookingDuration()))
                        .build())
                .toList();
    }

    @Override
    public Page<BookingResponse> getAllBookingsByPlayfieldId(UUID playfieldId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("startDateTime").descending());
        return bookingRepository.findAllByBookingPlayfield_Id(playfieldId, pageable)
                .map(BookingMapper::toBookingResponse);
    }

    @Override
    public Page<BookingResponse> getAllBookings(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("startDateTime").descending());
        return bookingRepository.findAll(pageable)
                .map(BookingMapper::toBookingResponse);
    }

    private Booking buildBooking(User user, Playfield playfield, CreateBookingRequest request, Double bookingPrice) {
        return Booking.builder()
                .bookingSport(sportService.findSportById(request.sportId()))
                .bookingPlayfield(playfield)
                .playfieldSport(getMatchingPlayfieldSport(playfield, request.sportId()))
                .user(user)
                .bookingStatus(determineBookingStatus(user))
                .bookingDate(request.bookingDate())
                .bookingTime(request.startTime())
                .bookingDuration(request.duration())
                .bookingNote(request.bookingNote())
                .bookingPrice(bookingPrice)
                .build();
    }

    private PlayfieldSport getMatchingPlayfieldSport(Playfield playfield, UUID sportId) {
        return playfield.getPlayfieldSportList().stream()
                .filter(sport -> sport.getSport().getId().equals(sportId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Sport not found"));
    }

    private BookingStatus determineBookingStatus(User user) {
        return user.getRoles().contains(Role.VENUE_OWNER) ? BookingStatus.CONFIRMED : BookingStatus.PENDING;
    }
}
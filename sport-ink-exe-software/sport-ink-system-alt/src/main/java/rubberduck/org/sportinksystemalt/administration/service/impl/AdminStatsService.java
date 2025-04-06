package rubberduck.org.sportinksystemalt.administration.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rubberduck.org.sportinksystemalt.administration.domain.dto.VenueRevenueDto;
import rubberduck.org.sportinksystemalt.administration.service.IAdminStatsService;
import rubberduck.org.sportinksystemalt.booking.repository.BookingRepository;
import rubberduck.org.sportinksystemalt.chatting.repository.ChatMessageRepository;
import rubberduck.org.sportinksystemalt.user.repository.UserRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Service
public class AdminStatsService implements IAdminStatsService {
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final ChatMessageRepository chatMessageRepository;

    public AdminStatsService(BookingRepository bookingRepository, UserRepository userRepository, ChatMessageRepository chatMessageRepository) {
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
        this.chatMessageRepository = chatMessageRepository;
    }

    @Override
    public long getTotalUsers(String startDate, String endDate) {
        return userRepository.countByCreatedAtBetween(parseToLocalDateTime(startDate), parseToLocalDateTime(endDate));
    }

    @Override
    public Map<String, Long> getMessagesPerDay(String startDate, String endDate) {
        return chatMessageRepository.countChatMessagesPerDay(parseToLocalDateTime(startDate), parseToLocalDateTime(endDate));
    }

    @Override
    public long getTotalBookings(String startDate, String endDate) {
        return bookingRepository.countBookingsByBookingDateBetween(parseToLocalDate(startDate), parseToLocalDate(endDate));
    }

    @Override
    public Map<LocalDate, Long> getBookingsPerDay(String startDate, String endDate) {
        return bookingRepository.countBookingsPerDay(parseToLocalDate(startDate), parseToLocalDate(endDate));
    }

    @Override
    public Map<String, Long> getTopVenues(String startDate, String endDate) {
        return bookingRepository.countBookingsPerVenue(parseToLocalDate(startDate), parseToLocalDate(endDate));
    }

    @Override
    public Page<VenueRevenueDto> getRevenueOfVenuesByDate(String date, int page, int size) {
        LocalDate parsedDate = parseToLocalDate(date);
        Pageable pageable = PageRequest.of(page, size);
        return bookingRepository.findRevenueOfVenuesByDate(parsedDate, pageable);
    }

    private LocalDate parseToLocalDate(String dateStr) {
        if (dateStr == null) {
            return LocalDate.now();
        }
        return LocalDate.parse(dateStr, DateTimeFormatter.ISO_DATE);
    }

    private LocalDateTime parseToLocalDateTime(String dateStr) {
        if (dateStr == null) {
            return LocalDateTime.now();
        }
        return LocalDate.parse(dateStr, DateTimeFormatter.ISO_DATE).atStartOfDay();
    }
}

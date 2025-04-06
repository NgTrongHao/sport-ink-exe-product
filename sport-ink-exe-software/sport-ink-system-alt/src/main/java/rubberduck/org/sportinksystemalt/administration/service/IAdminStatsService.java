package rubberduck.org.sportinksystemalt.administration.service;

import org.springframework.data.domain.Page;
import rubberduck.org.sportinksystemalt.administration.domain.dto.VenueRevenueDto;

import java.time.LocalDate;
import java.util.Map;

public interface IAdminStatsService {
    long getTotalUsers(String startDate, String endDate);

    Map<String, Long> getMessagesPerDay(String startDate, String endDate);

    long getTotalBookings(String startDate, String endDate);

    Map<LocalDate, Long> getBookingsPerDay(String startDate, String endDate);

    Map<String, Long> getTopVenues(String startDate, String endDate);

    Page<VenueRevenueDto> getRevenueOfVenuesByDate(String date, int page, int size);
}

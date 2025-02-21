package rubberduck.org.sportinksystemalt.administration.service;

import java.time.LocalDate;
import java.util.Map;

public interface IAdminStatsService {
    long getTotalUsers(String startDate, String endDate);

    Map<String, Long> getMessagesPerDay(String startDate, String endDate);

    long getTotalBookings(String startDate, String endDate);

    Map<LocalDate, Long> getBookingsPerDay(String startDate, String endDate);

    Map<String, Long> getTopVenues(String startDate, String endDate);
}

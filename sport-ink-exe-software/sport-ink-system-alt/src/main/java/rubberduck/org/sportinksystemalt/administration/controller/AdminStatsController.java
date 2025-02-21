package rubberduck.org.sportinksystemalt.administration.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import rubberduck.org.sportinksystemalt.administration.service.IAdminStatsService;
import rubberduck.org.sportinksystemalt.shared.domain.ApiResponse;

import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/stats")
@Tag(name = "Admin Stats")
public class AdminStatsController {

    private final IAdminStatsService adminStatsService;

    public AdminStatsController(IAdminStatsService adminStatsService) {
        this.adminStatsService = adminStatsService;
    }

    @GetMapping("/total-users")
    public ResponseEntity<ApiResponse<Long>> getTotalUsers(@RequestParam(required = false) String startDate, @RequestParam(required = false) String endDate) {
        long totalUsers = adminStatsService.getTotalUsers(startDate, endDate);
        return ResponseEntity.ok(
                ApiResponse.<Long>builder()
                        .code(200)
                        .message("Total users retrieved successfully")
                        .data(totalUsers)
                        .build()
        );
    }

    @GetMapping("/messages-per-day")
    public ResponseEntity<ApiResponse<Map<String, Long>>> getMessagesPerDay(@RequestParam(required = false) String startDate, @RequestParam(required = false) String endDate) {
        Map<String, Long> messagesPerDay = adminStatsService.getMessagesPerDay(startDate, endDate);
        return ResponseEntity.ok(
                ApiResponse.<Map<String, Long>>builder()
                        .code(200)
                        .message("Messages per day retrieved successfully")
                        .data(messagesPerDay)
                        .build()
        );
    }

    @GetMapping("/total-bookings")
    public ResponseEntity<ApiResponse<Long>> getTotalBookings(@RequestParam(required = false) String startDate, @RequestParam(required = false) String endDate) {
        long totalBookings = adminStatsService.getTotalBookings(startDate, endDate);
        return ResponseEntity.ok(
                ApiResponse.<Long>builder()
                        .code(200)
                        .message("Total bookings retrieved successfully")
                        .data(totalBookings)
                        .build()
        );
    }

    @GetMapping("/bookings-per-day")
    public ResponseEntity<ApiResponse<Map<LocalDate, Long>>> getBookingsPerDay(@RequestParam(required = false) String startDate, @RequestParam(required = false) String endDate) {
        Map<LocalDate, Long> bookingsPerDay = adminStatsService.getBookingsPerDay(startDate, endDate);
        return ResponseEntity.ok(
                ApiResponse.<Map<LocalDate, Long>>builder()
                        .code(200)
                        .message("Bookings per day retrieved successfully")
                        .data(bookingsPerDay)
                        .build()
        );
    }

    @GetMapping("/top-venues")
    public ResponseEntity<ApiResponse<Map<String, Long>>> getTopVenues(@RequestParam(required = false) String startDate, @RequestParam(required = false) String endDate) {
        Map<String, Long> topVenues = adminStatsService.getTopVenues(startDate, endDate);
        return ResponseEntity.ok(
                ApiResponse.<Map<String, Long>>builder()
                        .code(200)
                        .message("Top venues retrieved successfully")
                        .data(topVenues)
                        .build()
        );
    }
}

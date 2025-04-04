package rubberduck.org.sportinksystemalt.booking.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rubberduck.org.sportinksystemalt.booking.domain.dto.BookingResponse;
import rubberduck.org.sportinksystemalt.booking.domain.dto.CreateBookingRequest;
import rubberduck.org.sportinksystemalt.booking.domain.dto.TimeSlot;
import rubberduck.org.sportinksystemalt.booking.service.IBookingService;
import rubberduck.org.sportinksystemalt.shared.common.annotation.CurrentUser;
import rubberduck.org.sportinksystemalt.shared.domain.ApiResponse;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/bookings")
@Tag(name = "Booking", description = "The booking API")
public class BookingController {
    private final IBookingService bookingService;

    public BookingController(IBookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping("/owner/get-all/{playfield-id}")
    public ResponseEntity<ApiResponse<Page<BookingResponse>>> getAllBookingsByPlayfieldId(
            @PathVariable("playfield-id") UUID playfieldId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(
                ApiResponse.<Page<BookingResponse>>builder()
                        .code(200)
                        .message("Bookings fetched successfully")
                        .data(bookingService.getAllBookingsByPlayfieldId(playfieldId, page, size))
                        .build()
        );
    }

    @GetMapping("/{playfield-id}/booked-slots")
    public ResponseEntity<ApiResponse<List<TimeSlot>>> getPlayfieldAvailability(@PathVariable("playfield-id") UUID playfieldId, @RequestParam("date") LocalDate date) {
        return new ResponseEntity<>(
                ApiResponse.<List<TimeSlot>>builder()
                        .code(200)
                        .message("Playfield availability fetched successfully")
                        .data(bookingService.getBookingsByPlayfieldId(playfieldId, date))
                        .build(),
                HttpStatus.OK
        );
    }

    @PostMapping("/create")
    @PreAuthorize("hasAnyAuthority('VENUE_OWNER', 'PLAYER')")
    @Operation(
            summary = "Create Booking REST API",
            description = "Create Booking REST API is used to create a new booking."
    )
    public ResponseEntity<ApiResponse<String>> createBooking(@CurrentUser String username, @RequestBody @Valid CreateBookingRequest request) {
        bookingService.createBooking(username, request);
        return new ResponseEntity<>(
                ApiResponse.<String>builder()
                        .code(201)
                        .message("Booking created successfully")
                        .build(),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/get-all")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(
            summary = "Get All Bookings REST API",
            description = "Get All Bookings REST API is used to fetch all bookings with pagination. Only accessible by ADMIN."
    )
    public ResponseEntity<ApiResponse<Page<BookingResponse>>> getAllBookings(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(
                ApiResponse.<Page<BookingResponse>>builder()
                        .code(200)
                        .message("Bookings fetched successfully")
                        .data(bookingService.getAllBookings(page, size))
                        .build()
        );
    }
}

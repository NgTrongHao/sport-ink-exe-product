package rubberduck.org.sportinksystemalt.playfield.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rubberduck.org.sportinksystemalt.playfield.domain.dto.CreateVenueLocationRequest;
import rubberduck.org.sportinksystemalt.playfield.domain.dto.VenueLocationResponse;
import rubberduck.org.sportinksystemalt.playfield.service.IVenueLocationService;
import rubberduck.org.sportinksystemalt.shared.common.annotation.CurrentUser;
import rubberduck.org.sportinksystemalt.shared.domain.ApiResponse;

import java.util.UUID;

@RestController
@RequestMapping("/api/venue-locations")
@Tag(name = "Venue Location", description = "Venue Location REST API")
public class VenueLocationController {
    private final IVenueLocationService venueLocationService;

    public VenueLocationController(IVenueLocationService venueLocationService) {
        this.venueLocationService = venueLocationService;
    }

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('VENUE_OWNER')")
    @Operation(
            summary = "Add Venue Location REST API",
            description = "Add Venue Location REST API is used to add a new venue location."
    )
    public ResponseEntity<ApiResponse<String>> addVenueLocation(@CurrentUser String username, @RequestBody CreateVenueLocationRequest request) {
        venueLocationService.addVenueLocation(username, request);
        return ResponseEntity.ok(ApiResponse.<String>builder()
                .code(201)
                .message("Venue location added successfully")
                .build());
    }

    @GetMapping("get/{venue-location-id}")
    @Operation(
            summary = "Get Venue Location REST API",
            description = "Get Venue Location REST API is used to get a venue location."
    )
    public ResponseEntity<ApiResponse<VenueLocationResponse>> getVenueLocationById(@PathVariable("venue-location-id") UUID venueLocationId) {
        return ResponseEntity.ok(ApiResponse.<VenueLocationResponse>builder()
                .code(200)
                .message("Venue location retrieved successfully")
                .data(venueLocationService.getVenueLocationById(venueLocationId))
                .build());
    }

    @PatchMapping("/approve/{venue-location-id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(
            summary = "Approve Venue Location REST API",
            description = "Approve Venue Location REST API is used to approve a venue location."
    )
    public ResponseEntity<ApiResponse<String>> approveVenueLocation(@PathVariable("venue-location-id") UUID venueLocationId) {
        venueLocationService.approveVenueLocation(venueLocationId);
        return ResponseEntity.ok(ApiResponse.<String>builder()
                .code(200)
                .message("Venue location approved successfully")
                .build());
    }
}

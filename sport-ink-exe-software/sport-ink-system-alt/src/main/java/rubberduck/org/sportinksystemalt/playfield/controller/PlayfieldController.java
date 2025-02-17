package rubberduck.org.sportinksystemalt.playfield.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rubberduck.org.sportinksystemalt.playfield.domain.dto.CreatePlayfieldRequest;
import rubberduck.org.sportinksystemalt.playfield.domain.dto.PlayfieldResponse;
import rubberduck.org.sportinksystemalt.playfield.domain.dto.UpdatePricingBySportRequest;
import rubberduck.org.sportinksystemalt.playfield.service.IPlayfieldService;
import rubberduck.org.sportinksystemalt.shared.domain.ApiResponse;

import java.util.UUID;

@RestController
@RequestMapping("/api/playfields")
@Tag(name = "Playfield", description = "Playfield REST API")
public class PlayfieldController {
    private final IPlayfieldService playfieldService;

    public PlayfieldController(IPlayfieldService playfieldService) {
        this.playfieldService = playfieldService;
    }

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('VENUE_OWNER')")
    @Operation(
            summary = "Add Playfield REST API",
            description = "Add Playfield REST API is used to add a new playfield to the venue location."
    )
    public ResponseEntity<ApiResponse<String>> addPlayfield(@RequestBody CreatePlayfieldRequest request) {
        playfieldService.addPlayfield(request);
        return new ResponseEntity<>(ApiResponse.<String>builder()
                .code(201)
                .message("Playfield added successfully")
                .build(),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/get-playfields/{venue-location-id}")
    @Operation(
            summary = "Get Playfields By Venue Location ID REST API",
            description = "Get Playfields By Venue Location ID REST API is used to fetch all playfields by venue location ID."
    )
    public ResponseEntity<ApiResponse<Iterable<PlayfieldResponse>>> getPlayfieldsByVenueLocationId(@PathVariable("venue-location-id") UUID venueLocationId) {
        return ResponseEntity.ok(ApiResponse.<Iterable<PlayfieldResponse>>builder()
                .code(200)
                .message("Play fields fetched successfully")
                .data(playfieldService.getPlayfieldsByVenueLocationId(venueLocationId))
                .build());
    }

    @PatchMapping("/approve/{playfield-id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(
            summary = "Approve Playfield REST API",
            description = "Approve Playfield REST API is used to approve a playfield."
    )
    public ResponseEntity<ApiResponse<String>> approvePlayfield(@PathVariable("playfield-id") UUID playfieldId) {
        playfieldService.approvePlayfield(playfieldId);
        return ResponseEntity.ok(ApiResponse.<String>builder()
                .code(200)
                .message("Playfield approved successfully")
                .build());
    }

    @PutMapping("/update-playfield-price")
    @PreAuthorize("hasAuthority('VENUE_OWNER')")
    @Operation(
            summary = "Update Playfield Price REST API",
            description = "Update Playfield Price REST API is used to update the price of a playfield."
    )
    public ResponseEntity<ApiResponse<String>> updatePlayfieldPrice(@RequestBody UpdatePricingBySportRequest request) {
        playfieldService.updatePlayfieldPrice(request);
        return ResponseEntity.ok(ApiResponse.<String>builder()
                .code(200)
                .message("Playfield price updated successfully")
                .build());
    }
}

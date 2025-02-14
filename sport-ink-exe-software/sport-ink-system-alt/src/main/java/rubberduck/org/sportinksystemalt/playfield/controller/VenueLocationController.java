package rubberduck.org.sportinksystemalt.playfield.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rubberduck.org.sportinksystemalt.playfield.domain.dto.CreateVenueLocationRequest;
import rubberduck.org.sportinksystemalt.playfield.service.IVenueLocationService;
import rubberduck.org.sportinksystemalt.shared.common.annotation.CurrentUser;

@RestController
@RequestMapping("/api/venue-location")
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
    public void addVenueLocation(@CurrentUser String username, @RequestBody CreateVenueLocationRequest request) {
        venueLocationService.addVenueLocation(username, request);
    }
}

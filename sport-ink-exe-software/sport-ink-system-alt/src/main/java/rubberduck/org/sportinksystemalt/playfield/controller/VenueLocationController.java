package rubberduck.org.sportinksystemalt.playfield.controller;

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
public class VenueLocationController {
    private final IVenueLocationService venueLocationService;

    public VenueLocationController(IVenueLocationService venueLocationService) {
        this.venueLocationService = venueLocationService;
    }

    @PreAuthorize("hasAuthority('VENUE_OWNER')")
    @PostMapping("/add")
    public void addVenueLocation(@CurrentUser String username, @RequestBody CreateVenueLocationRequest request) {
        venueLocationService.addVenueLocation(username, request);
    }
}

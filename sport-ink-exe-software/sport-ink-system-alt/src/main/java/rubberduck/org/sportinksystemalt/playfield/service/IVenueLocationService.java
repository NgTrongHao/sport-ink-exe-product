package rubberduck.org.sportinksystemalt.playfield.service;

import rubberduck.org.sportinksystemalt.playfield.domain.dto.CreateVenueLocationRequest;
import rubberduck.org.sportinksystemalt.playfield.domain.entity.VenueLocation;

import java.util.UUID;

public interface IVenueLocationService {
    void addVenueLocation(String username, CreateVenueLocationRequest request);

    VenueLocation getVenueLocationById(UUID id);

    boolean isOwnerOfVenueLocation(UUID venueId, UUID ownerId);
}

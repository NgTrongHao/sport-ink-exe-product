package rubberduck.org.sportinksystemalt.playfield.service;

import rubberduck.org.sportinksystemalt.playfield.domain.dto.CreateVenueLocationRequest;

public interface IVenueLocationService {
    void addVenueLocation(String username, CreateVenueLocationRequest request);
}

package rubberduck.org.sportinksystemalt.playfield.service;

import rubberduck.org.sportinksystemalt.playfield.domain.dto.CreatePlayfieldRequest;
import rubberduck.org.sportinksystemalt.playfield.domain.dto.PlayfieldResponse;

import java.util.UUID;

public interface IPlayfieldService {
    void addPlayfield(CreatePlayfieldRequest request);

    Iterable<PlayfieldResponse> getPlayfieldsByVenueLocationId(UUID venueLocationId);
}

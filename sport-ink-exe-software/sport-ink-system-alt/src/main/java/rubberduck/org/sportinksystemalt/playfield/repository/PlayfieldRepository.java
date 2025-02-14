package rubberduck.org.sportinksystemalt.playfield.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rubberduck.org.sportinksystemalt.playfield.domain.dto.PlayfieldResponse;
import rubberduck.org.sportinksystemalt.playfield.domain.entity.Playfield;

import java.util.List;
import java.util.UUID;

public interface PlayfieldRepository extends JpaRepository<Playfield, UUID> {
    List<Playfield> findAllByVenueLocation_Id(UUID venueLocationId);
}
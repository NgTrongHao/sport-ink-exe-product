package rubberduck.org.sportinksystemalt.playfield.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rubberduck.org.sportinksystemalt.playfield.domain.entity.PlayfieldSport;

import java.util.UUID;

public interface PlayfieldSportRepository extends JpaRepository<PlayfieldSport, UUID> {
}

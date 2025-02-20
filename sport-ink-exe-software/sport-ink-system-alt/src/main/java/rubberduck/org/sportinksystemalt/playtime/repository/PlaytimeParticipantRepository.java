package rubberduck.org.sportinksystemalt.playtime.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rubberduck.org.sportinksystemalt.playtime.domain.entity.PlaytimeParticipant;

import java.util.UUID;

public interface PlaytimeParticipantRepository extends JpaRepository<PlaytimeParticipant, UUID> {

}

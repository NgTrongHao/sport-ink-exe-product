package rubberduck.org.sportinksystemalt.playdate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rubberduck.org.sportinksystemalt.playdate.domain.entity.PlaydateParticipant;

import java.util.UUID;

public interface PlaydateParticipantRepository extends JpaRepository<PlaydateParticipant, UUID> {

}

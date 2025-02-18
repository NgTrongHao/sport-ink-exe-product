package rubberduck.org.sportinksystemalt.playdate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rubberduck.org.sportinksystemalt.playdate.domain.entity.Playdate;

import java.util.UUID;

public interface PlaydateRepository extends JpaRepository<Playdate, UUID> {

}

package rubberduck.org.sportinksystemalt.playtime.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rubberduck.org.sportinksystemalt.playtime.domain.entity.Playtime;

import java.util.UUID;

public interface PlaytimeRepository extends JpaRepository<Playtime, UUID> {

}

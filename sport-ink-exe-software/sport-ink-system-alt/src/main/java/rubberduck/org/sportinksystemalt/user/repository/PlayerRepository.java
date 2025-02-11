package rubberduck.org.sportinksystemalt.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rubberduck.org.sportinksystemalt.user.domain.entity.Player;

import java.util.UUID;

public interface PlayerRepository extends JpaRepository<Player, UUID> {
}
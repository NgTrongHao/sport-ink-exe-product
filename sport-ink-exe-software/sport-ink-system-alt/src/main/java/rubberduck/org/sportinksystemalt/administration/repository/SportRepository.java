package rubberduck.org.sportinksystemalt.administration.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rubberduck.org.sportinksystemalt.administration.domain.entity.Sport;

import java.util.UUID;

public interface SportRepository extends JpaRepository<Sport, UUID> {
}
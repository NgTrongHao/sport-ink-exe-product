package rubberduck.org.sportinksystemalt.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rubberduck.org.sportinksystemalt.user.domain.entity.VenueOwner;

import java.util.UUID;

public interface VenueOwnerRepository extends JpaRepository<VenueOwner, UUID> {
}
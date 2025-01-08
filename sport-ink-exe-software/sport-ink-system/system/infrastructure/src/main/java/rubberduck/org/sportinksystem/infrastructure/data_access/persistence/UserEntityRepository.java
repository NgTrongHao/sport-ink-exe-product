package rubberduck.org.sportinksystem.infrastructure.data_access.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import rubberduck.org.sportinksystem.infrastructure.data_access.orm.UserEntity;

import java.util.UUID;

public interface UserEntityRepository extends JpaRepository<UserEntity, UUID> {
    boolean existsByEmail(String email);

    boolean existsByUsername(String username);
}
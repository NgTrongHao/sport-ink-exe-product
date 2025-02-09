package rubberduck.org.sportinksystemalt.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rubberduck.org.sportinksystemalt.user.domain.entity.User;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
    User findByUsernameandPassword(String username, String password);
}
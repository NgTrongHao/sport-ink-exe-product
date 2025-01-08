package rubberduck.org.sportinksystem.core.applicationservice.user.repository;

import rubberduck.org.sportinksystem.core.domain.entity.user.User;

public interface UserRepository {
    User save(User user);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);
}

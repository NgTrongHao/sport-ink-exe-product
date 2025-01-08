package rubberduck.org.sportinksystem.adapter.dsGateway;

import org.springframework.stereotype.Repository;
import rubberduck.org.sportinksystem.adapter.mapper.UserMapper;
import rubberduck.org.sportinksystem.core.applicationservice.user.repository.UserRepository;
import rubberduck.org.sportinksystem.core.domain.entity.user.User;
import rubberduck.org.sportinksystem.infrastructure.data_access.persistence.UserEntityRepository;

@Repository
public class UserDsGateway implements UserRepository {
    private final UserEntityRepository userEntityRepository;

    public UserDsGateway(UserEntityRepository userEntityRepository) {
        this.userEntityRepository = userEntityRepository;
    }

    @Override
    public User save(User user) {
        return UserMapper.mapToDomain(userEntityRepository.save(
                UserMapper.mapToEntity(user)
        ));
    }

    @Override
    public boolean existsByEmail(String email) {
        return userEntityRepository.existsByEmail(email);
    }

    @Override
    public boolean existsByUsername(String username) {
        return userEntityRepository.existsByUsername(username);
    }
}

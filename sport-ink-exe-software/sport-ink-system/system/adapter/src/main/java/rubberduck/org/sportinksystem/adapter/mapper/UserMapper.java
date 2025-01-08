package rubberduck.org.sportinksystem.adapter.mapper;

import org.springframework.stereotype.Component;
import rubberduck.org.sportinksystem.core.domain.entity.user.User;
import rubberduck.org.sportinksystem.core.domain.valueObject.FullName;
import rubberduck.org.sportinksystem.core.domain.valueObject.Role;
import rubberduck.org.sportinksystem.infrastructure.data_access.orm.UserEntity;

import java.util.stream.Collectors;

public class UserMapper {
    public static UserEntity mapToEntity(User user) {
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(user.getEmail().email());
        userEntity.setUsername(user.getUsername().username());
        userEntity.setFirstName(user.getFullName().firstName());
        userEntity.setMiddleName(user.getFullName().middleName());
        userEntity.setLastName(user.getFullName().lastName());
        userEntity.setPassword(user.getPassword());
        userEntity.setEnabled(user.isEnabled());
        userEntity.setVerified(user.isVerified());
        return userEntity;
    }

    public static User mapToDomain(UserEntity userEntity) {
        return new User(
                userEntity.getId(),
                userEntity.getEmail(),
                userEntity.getUsername(),
                new FullName(userEntity.getFirstName(), userEntity.getMiddleName(), userEntity.getLastName()),
                userEntity.getPassword(),
                userEntity.isEnabled(),
                userEntity.getRoles() != null ? userEntity.getRoles().stream().map(
                        roleEnum -> Role.valueOf(roleEnum.name())
                ).collect(Collectors.toSet()) : null,
                userEntity.isVerified(),
                userEntity.getCreatedAt(),
                userEntity.getUpdatedAt()
        );
    }
}

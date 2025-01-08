package rubberduck.org.sportinksystem.core.applicationservice.user.model;

import rubberduck.org.sportinksystem.core.domain.entity.user.User;

import java.time.LocalDateTime;

public record UserInfo(
        String id,
        String email,
        String username,
        String firstName,
        String middleName,
        String lastName,
        boolean enabled,
        boolean emailVerified,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static UserInfo userInfoFromUser(User user) {
        return new UserInfo(
                user.getId().toString(),
                user.getEmail().email(),
                user.getUsername().username(),
                user.getFullName().firstName(),
                user.getFullName().middleName(),
                user.getFullName().lastName(),
                user.isEnabled(),
                user.isVerified(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }
}

package rubberduck.org.sportinksystemalt.user.domain.dto;

import rubberduck.org.sportinksystemalt.user.domain.entity.Role;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

public record UserListResponse(
        UUID userId,
        String username,
        String email,
        String firstName,
        String lastName,
        String phoneNumber,
        Set<Role> roles,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
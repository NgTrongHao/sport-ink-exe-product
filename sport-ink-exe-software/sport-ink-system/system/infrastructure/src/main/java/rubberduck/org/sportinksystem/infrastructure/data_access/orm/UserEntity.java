package rubberduck.org.sportinksystem.infrastructure.data_access.orm;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "users", indexes = {
        @Index(name = "idx_user_entity_username", columnList = "username"),
        @Index(name = "idx_user_entity_id", columnList = "id")
}, uniqueConstraints = {
        @UniqueConstraint(name = "uc_user_entity_username", columnNames = {"username"}),
        @UniqueConstraint(name = "uc_user_entity_email", columnNames = {"email"})
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "last_name")
    private String lastName;

    @Column(nullable = false)
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "roles", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Set<RoleEnum> roles;

    @Column(nullable = false)
    private boolean enabled;

    @Column(nullable = false)
    private boolean verified;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }
}

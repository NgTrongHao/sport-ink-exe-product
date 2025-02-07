package rubberduck.org.sportinksystemalt.user.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(
        name = "\"user\"",
        indexes = {
                @Index(name = "idx_user_entity_username", columnList = "username"),
                @Index(name = "idx_user_entity_id", columnList = "user_id")
        },
        uniqueConstraints = {
                @UniqueConstraint(name = "uc_user_entity_username", columnNames = {"username"}),
                @UniqueConstraint(name = "uc_user_entity_email", columnNames = {"email"})
        }
)
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
@Builder
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(unique = true, nullable = false, updatable = false)
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

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Set<Role> roles = new HashSet<>();

    @Column(nullable = false)
    private boolean enabled;

    @Column(nullable = false)
    private boolean verified;

    @Column(name = "profile_picture")
    private String profilePicture;

    @Column(name = "cover_picture")
    private String coverPicture;

    @Column(columnDefinition = "TEXT")
    private String bio;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        enabled = true;
        verified = false;
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }
}

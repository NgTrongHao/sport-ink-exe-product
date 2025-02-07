package rubberduck.org.sportinksystemalt.user.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(
        name = "playfield_owner",
        indexes = {
                @Index(name = "idx_playfield_owner_user_id", columnList = "user_id")
        },
        uniqueConstraints = {
                @UniqueConstraint(name = "uc_playfield_owner_user_id", columnNames = {"user_id"})
        })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlayfieldOwner {

    @Id
    private UUID userId;

    @MapsId
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "phone_number", unique = true, nullable = false, length = 10, columnDefinition = "CHAR(10) CHECK (phone_number ~ '^[0-9]{10}$')")
    private String phoneNumber;

    @Column(name = "tax_number", nullable = false)
    private String taxNumber;
}

package rubberduck.org.sportinksystemalt.user.domain.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(
        name = "venue_owner",
        indexes = {
                @Index(name = "idx_venue_owner_user_id", columnList = "user_id")
        },
        uniqueConstraints = {
                @UniqueConstraint(name = "uc_venue_owner_user_id", columnNames = {"user_id"})
        })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VenueOwner {

    @Id
    private UUID userId;

    @MapsId
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    @Column(name = "tax_number", nullable = false)
    private String taxNumber;
}

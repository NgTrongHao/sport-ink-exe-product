package rubberduck.org.sportinksystemalt.user.domain.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "player", uniqueConstraints = {
        @UniqueConstraint(columnNames = "user_id")
}, indexes = {
        @Index(columnList = "user_id")
})
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Player {
    @Id
    private UUID userId;

    @MapsId
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    @Column(nullable = false)
    private Gender gender;

    @Column(name = "preferred_sport")
    private String preferredSport;
}

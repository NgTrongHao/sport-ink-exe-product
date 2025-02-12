package rubberduck.org.sportinksystemalt.playfield.domain.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "playfield_sport", indexes = {}, uniqueConstraints = {})
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PlayfieldSport {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sport_id")
    @ToString.Exclude
    private Sport sport;

    private boolean enabled;

    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "playfield_id")
    @JsonBackReference
    @ToString.Exclude
    private Playfield playfield;

    @OneToMany(mappedBy = "playfieldSport", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<PlayfieldPricing> pricingRules;
}

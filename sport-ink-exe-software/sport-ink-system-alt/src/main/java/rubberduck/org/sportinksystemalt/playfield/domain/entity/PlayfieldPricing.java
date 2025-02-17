package rubberduck.org.sportinksystemalt.playfield.domain.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.UUID;

@Entity
@Table(name = "pricing_rules", indexes = {
        @Index(name = "pricing_rules_playfield_sport_id_index", columnList = "playfield_sport_id")
}, uniqueConstraints = {})
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PlayfieldPricing {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "playfield_sport_id")
    @JsonBackReference
    private PlayfieldSport playfieldSport;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DayOfWeek dayOfWeek;

    @Column(nullable = false, columnDefinition = "TIME")
    private LocalTime startTime;

    @Column(nullable = false, columnDefinition = "TIME")
    private LocalTime endTime;

    private Double pricePerHour;

    private Double pricePerHalfHour;

    private String currency;
}

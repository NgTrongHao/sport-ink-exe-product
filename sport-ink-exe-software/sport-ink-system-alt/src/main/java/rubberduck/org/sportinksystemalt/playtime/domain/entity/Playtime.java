package rubberduck.org.sportinksystemalt.playtime.domain.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import rubberduck.org.sportinksystemalt.administration.domain.entity.Sport;
import rubberduck.org.sportinksystemalt.playfield.domain.entity.Playfield;
import rubberduck.org.sportinksystemalt.playfield.domain.entity.PlayfieldSport;
import rubberduck.org.sportinksystemalt.user.domain.entity.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "playtime", indexes = {
        @Index(columnList = "start_time"),
        @Index(columnList = "end_time"),
        @Index(columnList = "bookmaker_id"),
        @Index(columnList = "playfield_id"),
        @Index(columnList = "sport_id")
})
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Playtime {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "playfield_sport_id", nullable = false)
    private PlayfieldSport playfieldSport;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "playfield_id")
    @JsonBackReference
    @ToString.Exclude
    private Playfield playfield;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sport_id")
    @ToString.Exclude
    private Sport sport;



    // bookmaker
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bookmaker_id", nullable = false)
    private User bookmaker;




    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalDateTime endTime;

    @Column(name = "max_players", nullable = false)
    private int maxPlayers;

    @Column(nullable = false)
    private String status;

    @OneToMany(mappedBy = "playtime", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<PlaytimeParticipant> participants;

}

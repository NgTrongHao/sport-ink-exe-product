package rubberduck.org.sportinksystemalt.playdate.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "playdate", indexes = {
        @Index(columnList = "start_time"),
        @Index(columnList = "end_time")
})
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Playdate {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "playfield_sport_id", nullable = false)
    private UUID playfieldSportId;


    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalDateTime endTime;

    @Column(name = "max_players", nullable = false)
    private int maxPlayers;

    @Column(nullable = false)
    private String status;

    @OneToMany(mappedBy = "playdate", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<PlaydateParticipant> participants;

}

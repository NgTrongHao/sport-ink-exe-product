package rubberduck.org.sportinksystemalt.playdate.domain.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.*;

@Entity
@Table(name = "playdate_participant", indexes = {
        @Index(columnList = "playdate_id"),
        @Index(columnList = "user_id")
})

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PlaydateParticipant {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "playdate_id", nullable = false)
    private Playdate playdate;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(name = "joined_at", nullable = false)
    private LocalDateTime joinedAt;
}

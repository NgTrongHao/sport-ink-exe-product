package rubberduck.org.sportinksystemalt.playtime.domain.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.*;
import rubberduck.org.sportinksystemalt.chatting.domain.entity.ChatGroup;
import rubberduck.org.sportinksystemalt.user.domain.entity.User;

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
public class PlaytimeParticipant {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "playdate_id", nullable = false)
    @JsonBackReference
    private Playtime playtime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @ToString.Exclude
    private User user;

    @Column(name = "joined_at", nullable = false)
    private LocalDateTime joinedAt;

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private ParticipantRole role;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "chat_group_id")
    @ToString.Exclude
    private ChatGroup chatGroup;
}

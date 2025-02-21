package rubberduck.org.sportinksystemalt.chatting.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import rubberduck.org.sportinksystemalt.playtime.domain.entity.Playtime;
import rubberduck.org.sportinksystemalt.playtime.domain.entity.PlaytimeParticipant;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "chat_group", indexes = {}, uniqueConstraints = {})
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "chat_group_id", nullable = false, updatable = false)
    private UUID chatGroupId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "playtime_id", nullable = false)
    @ToString.Exclude
    private Playtime playtime;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "chatGroup", fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<ChatMessage> messages;

    @OneToMany(mappedBy = "chatGroup", fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<PlaytimeParticipant> participants;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean isDeleted;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}

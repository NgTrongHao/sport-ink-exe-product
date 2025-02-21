package rubberduck.org.sportinksystemalt.chatting.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import rubberduck.org.sportinksystemalt.user.domain.entity.User;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "chat_messages", indexes = {}, uniqueConstraints = {})
@Getter
@Setter
@Builder
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "chat_message_id", nullable = false)
    private UUID chatMessageId;

    @Column(nullable = false)
    private String message;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_group_id", nullable = false)
    @ToString.Exclude
    private ChatGroup chatGroup;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id", nullable = false)
    @ToString.Exclude
    private User sender;

    @Column(nullable = false)
    private LocalDateTime sentAt;

    @PrePersist
    public void prePersist() {
        this.sentAt = LocalDateTime.now();
    }
}

package rubberduck.org.sportinksystemalt.chatting.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rubberduck.org.sportinksystemalt.chatting.domain.entity.ChatGroup;
import rubberduck.org.sportinksystemalt.chatting.domain.entity.ChatMessage;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, UUID> {
    List<ChatMessage> findAllByChatGroup_ChatGroupIdOrderBySentAtDesc(UUID chatGroupChatGroupId, Pageable pageable);

    List<ChatMessage> findAllByChatGroupOrderBySentAtDesc(ChatGroup chatGroup, Pageable pageable);

    @Query("SELECT DATE(cm.sentAt) AS day, COUNT(cm) AS messageCount " +
            "FROM ChatMessage cm " +
            "WHERE cm.sentAt BETWEEN :startTime AND :endTime " +
            "GROUP BY DATE(cm.sentAt) " +
            "ORDER BY DATE(cm.sentAt)")
    Map<String, Long> countChatMessagesPerDay(@Param("startTime") LocalDateTime startTime,
                                              @Param("endTime") LocalDateTime endTime);
}
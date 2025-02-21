package rubberduck.org.sportinksystemalt.chatting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rubberduck.org.sportinksystemalt.chatting.domain.entity.ChatGroup;
import rubberduck.org.sportinksystemalt.playtime.domain.entity.Playtime;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ChatGroupRepository extends JpaRepository<ChatGroup, UUID> {
    Optional<ChatGroup> findByPlaytime_Id(UUID playtimeId);

    List<ChatGroup> findAllByPlaytimeIn(Collection<Playtime> playtimes);
}
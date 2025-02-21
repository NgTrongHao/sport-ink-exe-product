package rubberduck.org.sportinksystemalt.playtime.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rubberduck.org.sportinksystemalt.playtime.domain.entity.Playtime;
import rubberduck.org.sportinksystemalt.user.domain.entity.User;

import java.util.List;
import java.util.UUID;

public interface PlaytimeRepository extends JpaRepository<Playtime, UUID> {

    @Query("SELECT p FROM Playtime p JOIN p.participants pp WHERE pp.user = :user")
    List<Playtime> findByParticipantsUser(@Param("user") User user);

}

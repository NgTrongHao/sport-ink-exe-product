package rubberduck.org.sportinksystemalt.playtime.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rubberduck.org.sportinksystemalt.playtime.domain.entity.Playtime;
import rubberduck.org.sportinksystemalt.user.domain.entity.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface PlaytimeRepository extends JpaRepository<Playtime, UUID> {

    @Query("SELECT p FROM Playtime p JOIN p.participants pp WHERE pp.user = :user")
    List<Playtime> findByParticipantsUser(@Param("user") User user);

    @Query("SELECT p FROM Playtime p " +
            "JOIN p.playfield pf " +
            "JOIN pf.venueLocation vl " +
            "WHERE (:sportId IS NULL OR p.sport.id = :sportId) " +
            "AND (:city IS NULL OR LOWER(vl.city) LIKE CONCAT('%', LOWER(:city), '%')) " +
            "AND (:district IS NULL OR LOWER(vl.district) LIKE CONCAT('%', LOWER(:district), '%')) " +
            "AND (:ward IS NULL OR LOWER(vl.ward) LIKE CONCAT('%', LOWER(:ward), '%')) " +
            "AND (p.startTime BETWEEN :startDate AND :endDate)")
    Page<Playtime> searchPlaytimes(@Param("sportId") UUID sportId,
                                   @Param("city") String city,
                                   @Param("district") String district,
                                   @Param("ward") String ward,
                                   @Param("startDate") LocalDateTime startDate,
                                   @Param("endDate") LocalDateTime endDate,
                                   Pageable pageable);
  
}

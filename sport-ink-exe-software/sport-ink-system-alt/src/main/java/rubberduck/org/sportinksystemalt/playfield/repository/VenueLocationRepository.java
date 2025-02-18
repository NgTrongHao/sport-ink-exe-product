package rubberduck.org.sportinksystemalt.playfield.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rubberduck.org.sportinksystemalt.playfield.domain.entity.VenueLocation;

import java.util.List;
import java.util.UUID;

public interface VenueLocationRepository extends JpaRepository<VenueLocation, UUID> {
    @Query(value = """
                SELECT v.*,
                       (6371 * acos(cos(radians(:latitude))
                       * cos(radians(v.latitude))
                       * cos(radians(v.longitude) - radians(:longitude))
                       + sin(radians(:latitude))
                       * sin(radians(v.latitude)))) AS distance
                FROM venue_location v
                JOIN playfield pf ON v.id = pf.venue_location_id
                JOIN playfield_sport pf_sport ON pf.id = pf_sport.playfield_id
                JOIN sport s ON pf_sport.sport_id = s.id
                WHERE s.name IN :sports
                HAVING distance < :radius
                ORDER BY distance
            """, nativeQuery = true)
    List<VenueLocation> findNearbyVenues(@Param("latitude") double latitude,
                                         @Param("longitude") double longitude,
                                         @Param("radius") double radius,
                                         @Param("sports") List<String> sports
    );

    @Query("SELECT v FROM VenueLocation v WHERE v.geohash LIKE :geohashPrefix%")
    List<VenueLocation> findNearbyVenuesByGeohash(@Param("geohashPrefix") String geohashPrefix);

    @Query("SELECT v FROM VenueLocation v WHERE LOWER(CONCAT(v.ward, ' ', v.district, ' ', v.city)) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    List<VenueLocation> searchByAddress(@Param("searchTerm") String searchTerm);

}
package rubberduck.org.sportinksystemalt.playfield.domain.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import rubberduck.org.sportinksystemalt.shared.common.util.GeohashUtil;
import rubberduck.org.sportinksystemalt.user.domain.entity.VenueOwner;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "venue_location", indexes = {
        @Index(name = "idx_latitude_longitude", columnList = "latitude, longitude"),
        @Index(name = "idx_address_components", columnList = "ward, district, city")
}, uniqueConstraints = {})
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class VenueLocation {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String address;

    @Column(name = "phone_contact", nullable = false)
    private String phoneContact;

    private Double latitude;

    private Double longitude;

    @Column(nullable = false)
    private String ward;

    @Column(nullable = false)
    private String district;

    @Column(nullable = false)
    private String city;

    private String description;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "venue_location_image_urls", joinColumns = @JoinColumn(name = "venue_location_id"))
    private List<String> imageUrls;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean enabled;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "venue_owner_id")
    @ToString.Exclude
    private VenueOwner venueOwner;

    @OneToMany(mappedBy = "venueLocation", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    @ToString.Exclude
    private List<OpeningHours> openingHoursList;

    @Column(name = "average_rating")
    private Double averageRating;

    @Column(name = "total_rating")
    private Integer totalRating;

    @Column(name = "geohash")
    private String geohash;

    @OneToMany(mappedBy = "venueLocation", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    @ToString.Exclude
    private List<Playfield> playfieldList;

    @PrePersist
    @PreUpdate
    private void updateGeohash() {
        if (latitude != null && longitude != null) {
            this.geohash = GeohashUtil.encode(latitude, longitude);
        }
    }
}

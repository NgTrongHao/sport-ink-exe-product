package rubberduck.org.sportinksystemalt.playfield.domain.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import rubberduck.org.sportinksystemalt.user.domain.entity.VenueOwner;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "playfield", indexes = {
        @Index(name = "venue_owner_id_index", columnList = "venue_owner_id"),
        @Index(name = "venue_location_id_index", columnList = "venue_location_id")
}, uniqueConstraints = {})
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Playfield {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;

    private String description;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "playfield_image_urls", joinColumns = @JoinColumn(name = "playfield_id"))
    private List<String> imageUrls;

    private boolean enabled;

    @ManyToOne
    @JoinColumn(name = "venue_owner_id")
    private VenueOwner venueOwner;

    @ManyToOne
    @JoinColumn(name = "venue_location_id")
    private VenueLocation venueLocation;

    @OneToMany(mappedBy = "playfield", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    @ToString.Exclude
    private List<PlayfieldSport> playfieldSportList;
}

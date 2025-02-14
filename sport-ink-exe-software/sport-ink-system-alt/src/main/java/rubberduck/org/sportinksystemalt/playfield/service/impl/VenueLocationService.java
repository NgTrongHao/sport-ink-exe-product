package rubberduck.org.sportinksystemalt.playfield.service.impl;

import org.springframework.stereotype.Service;
import rubberduck.org.sportinksystemalt.playfield.domain.dto.CreateVenueLocationRequest;
import rubberduck.org.sportinksystemalt.playfield.domain.entity.VenueLocation;
import rubberduck.org.sportinksystemalt.playfield.repository.VenueLocationRepository;
import rubberduck.org.sportinksystemalt.playfield.service.IVenueLocationService;
import rubberduck.org.sportinksystemalt.shared.common.util.GeohashUtil;
import rubberduck.org.sportinksystemalt.user.repository.VenueOwnerRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class VenueLocationService implements IVenueLocationService {
    private final VenueLocationRepository venueLocationRepository;
    private final VenueOwnerRepository venueOwnerRepository;

    public VenueLocationService(VenueLocationRepository venueLocationRepository, VenueOwnerRepository venueOwnerRepository) {
        this.venueLocationRepository = venueLocationRepository;
        this.venueOwnerRepository = venueOwnerRepository;
    }

    @Override
    public void addVenueLocation(String username, CreateVenueLocationRequest request) {
        VenueLocation venueLocation = VenueLocation.builder()
                .address(request.address())
                .latitude(request.latitude())
                .longitude(request.longitude())
                .description(request.description())
                .imageUrls(request.imageUrls())
                .phoneContact(request.phoneContact())
                .opening(request.opening())
                .closing(request.closing())
                .venueOwner(venueOwnerRepository.findByUser_Username(username))
                .build();
        venueLocationRepository.save(venueLocation);
    }

    @Override
    public VenueLocation getVenueLocationById(UUID id) {
        return venueLocationRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Venue location not found"));
    }

    @Override
    public boolean isOwnerOfVenueLocation(UUID venueId, UUID ownerId) {
        VenueLocation venueLocation = getVenueLocationById(venueId);
        return venueLocation.getVenueOwner().getUserId().equals(ownerId);
    }

    public List<VenueLocation> findNearbyVenues(double latitude, double longitude, double radius) {

        // Get the geohash prefix of the given latitude and longitude
        String geohashPrefix = GeohashUtil.encode(latitude, longitude).substring(0, 6);

        // Find nearby venues by the geohash prefix
        List<VenueLocation> nearbyVenues = venueLocationRepository.findNearbyVenuesByGeohash(geohashPrefix);

        // Filter the nearby venues by the given radius
        return nearbyVenues.stream()
                .filter(venue -> calculateDistance(latitude, longitude, venue.getLatitude(), venue.getLongitude()) <= radius)
                .collect(Collectors.toList());
    }

    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371; // Radius of Earth in kilometers
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c; // Distance in kilometers
    }
}

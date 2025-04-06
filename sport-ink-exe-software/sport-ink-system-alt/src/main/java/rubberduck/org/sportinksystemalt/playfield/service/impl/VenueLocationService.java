package rubberduck.org.sportinksystemalt.playfield.service.impl;

import org.springframework.stereotype.Service;
import rubberduck.org.sportinksystemalt.playfield.domain.dto.CreateVenueLocationRequest;
import rubberduck.org.sportinksystemalt.playfield.domain.dto.OpeningHoursDTO;
import rubberduck.org.sportinksystemalt.playfield.domain.dto.VenueLocationResponse;
import rubberduck.org.sportinksystemalt.playfield.domain.entity.OpeningHours;
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
                .name(request.name())
                .address(request.address())
                .ward(request.ward())
                .district(request.district())
                .city(request.city())
                .latitude(request.latitude())
                .longitude(request.longitude())
                .description(request.description())
                .imageUrls(request.imageUrls())
                .phoneContact(request.phoneContact())
                .venueOwner(venueOwnerRepository.findByUser_Username(username))
                .build();

        List<OpeningHours> openingHours = createOpeningHours(venueLocation, request.openingHours());
        venueLocation.setOpeningHoursList(openingHours);

        venueLocationRepository.save(venueLocation);
    }

    @Override
    public VenueLocation findVenueLocationById(UUID id) {
        return venueLocationRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Venue location not found"));
    }

    @Override
    public boolean isOwnerOfVenueLocation(UUID venueId, UUID ownerId) {
        VenueLocation venueLocation = findVenueLocationById(venueId);
        return venueLocation.getVenueOwner().getUserId().equals(ownerId);
    }

    @Override
    public void approveVenueLocation(UUID venueLocationId) {
        VenueLocation venueLocation = venueLocationRepository.findById(venueLocationId)
                .orElseThrow(() -> new IllegalArgumentException("Venue location not found"));
        venueLocation.setEnabled(true);
        venueLocationRepository.save(venueLocation);
    }

    @Override
    public VenueLocationResponse getVenueLocationById(UUID venueLocationId) {
        VenueLocation venueLocation = findVenueLocationById(venueLocationId);

        return getVenueLocationResponse(venueLocation);
    }

    private VenueLocationResponse getVenueLocationResponse(VenueLocation venueLocation) {
        List<OpeningHoursDTO> openingHoursDTOs = venueLocation.getOpeningHoursList().stream()
                .map(this::convertToOpeningHoursDTO)
                .toList();

        return new VenueLocationResponse(
                venueLocation.getId(),
                venueLocation.getName(),
                venueLocation.getAddress(),
                venueLocation.getWard(),
                venueLocation.getDistrict(),
                venueLocation.getCity(),
                venueLocation.getLatitude(),
                venueLocation.getLongitude(),
                venueLocation.getDescription(),
                venueLocation.getImageUrls(),
                venueLocation.getPhoneContact(),
                openingHoursDTOs
        );
    }

    private List<OpeningHours> createOpeningHours(VenueLocation venueLocation, List<OpeningHoursDTO> openingHoursDTOs) {
        return openingHoursDTOs.stream()
                .map(dto -> OpeningHours.builder()
                        .venueLocation(venueLocation)
                        .dayOfWeek(dto.dayOfWeek())
                        .openingTime(dto.openingTime())
                        .closingTime(dto.closingTime())
                        .build())
                .collect(Collectors.toList());
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

    private OpeningHoursDTO convertToOpeningHoursDTO(OpeningHours openingHours) {
        return new OpeningHoursDTO(
                openingHours.getDayOfWeek(),
                openingHours.getOpeningTime(),
                openingHours.getClosingTime()
        );
    }

    // Add this method to the VenueLocationService class
    
    @Override
    public List<VenueLocationResponse> getVenueLocationsByOwner(String username) {
        // Get the venue owner by username
        var venueOwner = venueOwnerRepository.findByUser_Username(username);
        if (venueOwner == null) {
            throw new IllegalArgumentException("Venue owner not found");
        }
        
        // Find all venue locations by venue owner
        List<VenueLocation> venueLocations = venueLocationRepository.findAllByVenueOwner(venueOwner);
        
        // Convert to DTOs
        return venueLocations.stream()
                .map(this::getVenueLocationResponse)
                .collect(Collectors.toList());
    }
}
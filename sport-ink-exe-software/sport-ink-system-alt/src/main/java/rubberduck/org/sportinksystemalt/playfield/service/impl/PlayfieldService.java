package rubberduck.org.sportinksystemalt.playfield.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rubberduck.org.sportinksystemalt.administration.domain.dto.SportResponse;
import rubberduck.org.sportinksystemalt.administration.service.ISportService;
import rubberduck.org.sportinksystemalt.playfield.domain.dto.CreatePlayfieldRequest;
import rubberduck.org.sportinksystemalt.playfield.domain.dto.PlayfieldResponse;
import rubberduck.org.sportinksystemalt.playfield.domain.dto.PlayfieldSportResponse;
import rubberduck.org.sportinksystemalt.playfield.domain.dto.UpdatePricingBySportRequest;
import rubberduck.org.sportinksystemalt.playfield.domain.entity.Playfield;
import rubberduck.org.sportinksystemalt.playfield.domain.entity.PlayfieldPricing;
import rubberduck.org.sportinksystemalt.playfield.domain.entity.PlayfieldSport;
import rubberduck.org.sportinksystemalt.playfield.repository.PlayfieldRepository;
import rubberduck.org.sportinksystemalt.playfield.service.IPlayfieldService;
import rubberduck.org.sportinksystemalt.playfield.service.IVenueLocationService;
import rubberduck.org.sportinksystemalt.user.service.IUserService;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PlayfieldService implements IPlayfieldService {
    private final PlayfieldRepository playfieldRepository;
    private final IVenueLocationService venueLocationService;
    private final IUserService userService;
    private final ISportService sportService;

    public PlayfieldService(PlayfieldRepository playfieldRepository, IVenueLocationService venueLocationService, IUserService userService, ISportService sportService) {
        this.playfieldRepository = playfieldRepository;
        this.venueLocationService = venueLocationService;
        this.userService = userService;
        this.sportService = sportService;
    }

    @Override
    @Transactional
    public void addPlayfield(CreatePlayfieldRequest request) {
        validateVenueOwnership(request.venueId(), request.venueOwnerId());

        List<PlayfieldSport> playfieldSportList = createPlayfieldSports(request.sportIds());

        Playfield playfield = buildPlayfield(request, playfieldSportList);

        playfieldSportList.forEach(playfieldSport -> playfieldSport.setPlayfield(playfield));

        playfieldRepository.save(playfield);
    }

    @Override
    public Iterable<PlayfieldResponse> getPlayfieldsByVenueLocationId(UUID venueLocationId) {
        List<Playfield> playfields = playfieldRepository.findAllByVenueLocation_Id(venueLocationId);
        return playfields.stream()
                .map(this::convertToPlayfieldResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void approvePlayfield(UUID playfieldId) {
        Playfield playfield = playfieldRepository.findById(playfieldId)
                .orElseThrow(() -> new IllegalArgumentException("Playfield not found"));

        playfield.setEnabled(true);
        playfieldRepository.save(playfield);
    }

    @Override
    public void updatePlayfieldPrice(UpdatePricingBySportRequest request) {
        Playfield playfield = playfieldRepository.findById(request.playfieldId())
                .orElseThrow(() -> new IllegalArgumentException("Playfield not found"));

        PlayfieldSport playfieldSport = playfield.getPlayfieldSportList().stream()
                .filter(ps -> ps.getSport().getId().equals(request.sportId()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Sport not found"));

        validatePricingRules(request.pricingRules());

        playfieldSport.getPricingRules().clear();

        List<PlayfieldPricing> newPricingRules = request.pricingRules().stream()
                .map(rule -> PlayfieldPricing.builder()
                        .playfieldSport(playfieldSport)
                        .dayOfWeek(rule.dayOfWeek())
                        .startTime(rule.startTime())
                        .endTime(rule.endTime())
                        .pricePerHour(rule.pricePerHour())
                        .pricePerHalfHour(rule.pricePerHalfHour())
                        .currency(rule.currency())
                        .build()).toList();

        playfieldSport.getPricingRules().addAll(newPricingRules);

        playfieldRepository.save(playfield);
    }

    @Override
    public Playfield findPlayfieldById(UUID id) {
        return playfieldRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Playfield not found")
        );
    }

    private void validateVenueOwnership(UUID venueId, UUID venueOwnerId) {
        if (!venueLocationService.isOwnerOfVenueLocation(venueId, venueOwnerId)) {
            throw new IllegalArgumentException("Venue owner is not the owner of the venue location");
        }
    }

    private List<PlayfieldSport> createPlayfieldSports(List<UUID> sportIds) {
        return sportIds.stream()
                .map(sportId -> PlayfieldSport.builder()
                        .sport(sportService.findSportById(sportId))
                        .build())
                .collect(Collectors.toList());
    }

    private void validatePricingRules(List<UpdatePricingBySportRequest.PricingRule> rules) {
        Map<DayOfWeek, List<Map.Entry<LocalTime, LocalTime>>> dayIntervals = new HashMap<>();

        // Validate time intervals
        for (var rule : rules) {
            // Check if start time is before end time
            if (rule.startTime().isAfter(rule.endTime())) {
                throw new IllegalArgumentException("Start time must be before end time");
            }
            dayIntervals.computeIfAbsent(rule.dayOfWeek(), k -> new ArrayList<>())
                    .add(Map.entry(rule.startTime(), rule.endTime()));
        }

        // Check for overlapping time intervals
        for (var entry : dayIntervals.entrySet()) {
            List<Map.Entry<LocalTime, LocalTime>> intervals = entry.getValue().stream()
                    .sorted(Map.Entry.comparingByKey())
                    .toList();

            for (int i = 1; i < intervals.size(); i++) {
                if (!intervals.get(i).getKey().isAfter(intervals.get(i - 1).getValue()) &&
                        !intervals.get(i).getKey().equals(intervals.get(i - 1).getValue())) {
                    throw new IllegalArgumentException("Time intervals cannot overlap for " + entry.getKey());
                }
            }
        }
    }

    private Playfield buildPlayfield(CreatePlayfieldRequest request, List<PlayfieldSport> playfieldSportList) {
        return Playfield.builder()
                .name(request.name())
                .venueLocation(venueLocationService.findVenueLocationById(request.venueId()))
                .venueOwner(userService.getVenueOwnerById(request.venueOwnerId()))
                .playfieldSportList(playfieldSportList)
                .build();
    }

    private PlayfieldResponse convertToPlayfieldResponse(Playfield playfield) {
        return new PlayfieldResponse(
                playfield.getId(),
                playfield.getName(),
                playfield.getDescription(),
                playfield.getImageUrls(),
                playfield.isEnabled(),
                playfield.getPlayfieldSportList().stream()
                        .map(this::convertToPlayfieldSportResponse)
                        .collect(Collectors.toList())
        );
    }

    private PlayfieldSportResponse convertToPlayfieldSportResponse(PlayfieldSport playfieldSport) {
        SportResponse sportResponse = new SportResponse(
                playfieldSport.getSport().getId(),
                playfieldSport.getSport().getName(),
                playfieldSport.getSport().getDescription(),
                playfieldSport.getSport().getIconUrl()
        );
        return new PlayfieldSportResponse(sportResponse, playfieldSport.isEnabled());
    }
}

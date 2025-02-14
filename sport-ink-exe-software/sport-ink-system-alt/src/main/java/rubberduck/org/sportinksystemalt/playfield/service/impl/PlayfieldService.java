package rubberduck.org.sportinksystemalt.playfield.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rubberduck.org.sportinksystemalt.administration.domain.dto.SportResponse;
import rubberduck.org.sportinksystemalt.administration.service.ISportService;
import rubberduck.org.sportinksystemalt.playfield.domain.dto.CreatePlayfieldRequest;
import rubberduck.org.sportinksystemalt.playfield.domain.dto.PlayfieldResponse;
import rubberduck.org.sportinksystemalt.playfield.domain.dto.PlayfieldSportResponse;
import rubberduck.org.sportinksystemalt.playfield.domain.entity.Playfield;
import rubberduck.org.sportinksystemalt.playfield.domain.entity.PlayfieldSport;
import rubberduck.org.sportinksystemalt.playfield.repository.PlayfieldRepository;
import rubberduck.org.sportinksystemalt.playfield.service.IPlayfieldService;
import rubberduck.org.sportinksystemalt.playfield.service.IVenueLocationService;
import rubberduck.org.sportinksystemalt.user.service.IUserService;

import java.util.List;
import java.util.UUID;
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

    private Playfield buildPlayfield(CreatePlayfieldRequest request, List<PlayfieldSport> playfieldSportList) {
        return Playfield.builder()
                .name(request.name())
                .venueLocation(venueLocationService.getVenueLocationById(request.venueId()))
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

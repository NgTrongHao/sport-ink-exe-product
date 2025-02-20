package rubberduck.org.sportinksystemalt.playtime.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rubberduck.org.sportinksystemalt.playfield.domain.entity.Playfield;
import rubberduck.org.sportinksystemalt.playfield.service.IPlayfieldService;
import rubberduck.org.sportinksystemalt.playtime.domain.dto.CreatePlaytimeRequest;
import rubberduck.org.sportinksystemalt.playtime.domain.dto.PlaytimeParticipantResponse;
import rubberduck.org.sportinksystemalt.playtime.domain.dto.PlaytimeResponse;
import rubberduck.org.sportinksystemalt.playtime.domain.entity.ParticipantRole;
import rubberduck.org.sportinksystemalt.playtime.domain.entity.Playtime;
import rubberduck.org.sportinksystemalt.playtime.domain.entity.PlaytimeParticipant;
import rubberduck.org.sportinksystemalt.playtime.repository.PlaytimeParticipantRepository;
import rubberduck.org.sportinksystemalt.playtime.repository.PlaytimeRepository;
import rubberduck.org.sportinksystemalt.playtime.service.IPlaytimeService;
import rubberduck.org.sportinksystemalt.playfield.domain.entity.PlayfieldSport;
import rubberduck.org.sportinksystemalt.user.domain.entity.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
public class PlaytimeService implements IPlaytimeService {

    private static final Logger log = LoggerFactory.getLogger(PlaytimeService.class);

    private final PlaytimeRepository playtimeRepository;
    private final PlaytimeParticipantRepository playtimeParticipantRepository;
    private final IPlayfieldService playfieldService;

    public PlaytimeService(PlaytimeRepository playtimeRepository,
                           PlaytimeParticipantRepository playtimeParticipantRepository,
                           IPlayfieldService playfieldService) {
        this.playtimeRepository = playtimeRepository;
        this.playtimeParticipantRepository = playtimeParticipantRepository;
        this.playfieldService = playfieldService;
    }
    @Override
    public PlaytimeResponse createPlaytime(CreatePlaytimeRequest request) {

        log.info("PlaytimeServiceImpl - createPlaytime() - start");
        // validate
        if (!request.startTime().isBefore(request.endTime())) {
            throw new IllegalArgumentException("startTime must be before endTime");
        }

        if (request.maxPlayers() <= 0) {
            throw new IllegalArgumentException("maxPlayers must be greater than 0");
        }


        // Getplayfield by playfieldId
        Playfield playfield = playfieldService.getPlayfieldById(request.playfieldId());
        if (playfield == null) {
            throw new RuntimeException("Playfield not found with id: " + request.playfieldId());
        }
        //Check the playfield whether it supports selected sport or not
        PlayfieldSport playfieldSport = playfield.getPlayfieldSportList().stream()
                .filter(ps -> ps.getSport() != null && ps.getSport().getId().equals(request.sportId()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Playfield does not support the given sport id: " + request.sportId()));

        Playtime playtime = Playtime.builder()
                .playfieldSport(playfieldSport)
                .playfield(playfield)
                .sport(playfieldSport.getSport())
                .startTime(request.startTime())
                .endTime(request.endTime())
                .maxPlayers(request.maxPlayers())
                .status("OPEN")
                .participants(new ArrayList<>())
                .build();
        //TODO: fix later this line || bookmaker will take from security context. IDK what kind is it.
        User bookmaker = new User();
        bookmaker.setUserId(request.bookmakerId());
        PlaytimeParticipant bookmakerParticipant = PlaytimeParticipant.builder()
                .playtime(playtime)
                .user(bookmaker)
                .joinedAt(LocalDateTime.now())
                .role(ParticipantRole.BOOKMAKER)
                .build();
        playtime.getParticipants().add(bookmakerParticipant);

        Playtime savedPlaytime = playtimeRepository.save(playtime);
        log.info("PlaytimeServiceImpl - createPlaytime() - end");
        return mapToPlaytimeResponse(savedPlaytime);
    }

    @Override
    public PlaytimeResponse getPlaytimeById(UUID id) {
        log.info("PlaytimeServiceImpl - getPlaytimeById() - start");
        Playtime playtime = playtimeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Playdate does not exist! id: " + id));
        log.info("PlaytimeServiceImpl - getPlaytimeById() - end");
        return mapToPlaytimeResponse(playtime);
    }

    @Override
    public Page<PlaytimeResponse> getPlaytimesPageable(int page, int size) {
        log.info("PlaytimeServiceImpl - getPlaytimesPageable() - start");
        Pageable pageable = PageRequest.of(page, size);
        Page<Playtime> playdatePage = playtimeRepository.findAll(pageable);

        List<PlaytimeResponse> responses = playdatePage.getContent().stream()
                .map(this::mapToPlaytimeResponse)
                .collect(Collectors.toList());
        log.info("PlaytimeServiceImpl - getPlaytimesPageable() - end");
        return new PageImpl<>(responses, pageable, playdatePage.getTotalElements());
    }

    @Override
    public void deletePlaytime(UUID id) {
        log.info("PlaytimeServiceImpl - deletePlaytime() - start");

        Playtime playtime = playtimeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Playdate does not exist! id: " + id));
        playtime.setStatus("CLOSED");
        log.info("PlaytimeServiceImpl - deletePlaytime() - end");

        playtimeRepository.save(playtime);
    }

    private PlaytimeResponse mapToPlaytimeResponse(Playtime playtime) {
        List<PlaytimeParticipantResponse> participantResponses = playtime.getParticipants() == null
                ? List.of()
                : playtime.getParticipants().stream()
                .map(p -> new PlaytimeParticipantResponse(p.getUser().getUserId(), p.getJoinedAt(), p.getRole()))
                .collect(Collectors.toList());

        return new PlaytimeResponse(
                playtime.getId(),
                playtime.getPlayfieldSport() != null ? playtime.getPlayfieldSport().getId() : null,
                playtime.getPlayfield() != null ? playtime.getPlayfield().getId() : null,
                playtime.getSport() != null ? playtime.getSport().getId() : null,
                playtime.getBookmaker() != null ? playtime.getBookmaker().getUserId() : null,
                playtime.getStartTime(),
                playtime.getEndTime(),
                playtime.getMaxPlayers(),
                playtime.getStatus(),
                participantResponses
        );
    }
}

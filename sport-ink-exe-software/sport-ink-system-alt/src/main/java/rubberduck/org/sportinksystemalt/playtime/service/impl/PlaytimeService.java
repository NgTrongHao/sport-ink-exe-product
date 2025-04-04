package rubberduck.org.sportinksystemalt.playtime.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rubberduck.org.sportinksystemalt.playfield.domain.entity.Playfield;
import rubberduck.org.sportinksystemalt.playfield.domain.entity.PlayfieldSport;
import rubberduck.org.sportinksystemalt.playfield.service.IPlayfieldService;
import rubberduck.org.sportinksystemalt.playtime.domain.dto.CreatePlaytimeRequest;
import rubberduck.org.sportinksystemalt.playtime.domain.dto.PlaytimeParticipantResponse;
import rubberduck.org.sportinksystemalt.playtime.domain.dto.PlaytimeResponse;
import rubberduck.org.sportinksystemalt.playtime.domain.entity.ParticipantRole;
import rubberduck.org.sportinksystemalt.playtime.domain.entity.Playtime;
import rubberduck.org.sportinksystemalt.playtime.domain.entity.PlaytimeParticipant;
import rubberduck.org.sportinksystemalt.playtime.domain.entity.PlaytimeStatus;
import rubberduck.org.sportinksystemalt.playtime.repository.PlaytimeParticipantRepository;
import rubberduck.org.sportinksystemalt.playtime.repository.PlaytimeRepository;
import rubberduck.org.sportinksystemalt.playtime.service.IPlaytimeService;
import rubberduck.org.sportinksystemalt.shared.exception.handler.ResourceNotFoundException;
import rubberduck.org.sportinksystemalt.user.domain.entity.User;
import rubberduck.org.sportinksystemalt.user.service.IUserService;

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
    private final IUserService userService;

    public PlaytimeService(PlaytimeRepository playtimeRepository,
                           PlaytimeParticipantRepository playtimeParticipantRepository,
                           IPlayfieldService playfieldService,
                           IUserService userService) {
        this.playtimeRepository = playtimeRepository;
        this.playtimeParticipantRepository = playtimeParticipantRepository;
        this.playfieldService = playfieldService;
        this.userService = userService;
    }

    @Override
    public PlaytimeResponse createPlaytime(String username, CreatePlaytimeRequest request) {

        log.info("PlaytimeService - createPlaytime() - start");
        // validate
        if (!request.startTime().isBefore(request.endTime())) {
            throw new IllegalArgumentException("startTime must be before endTime");
        }

        if (request.startTime().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("startTime cannot be sooner than present");
        }
        if (request.maxPlayers() <= 0) {
            throw new IllegalArgumentException("maxPlayers must be greater than 0");
        }


        // Get playfield by playfieldId
        Playfield playfield = playfieldService.findPlayfieldById(request.playfieldId());
        if (playfield == null) {
            throw new IllegalArgumentException("Playfield not found with id: " + request.playfieldId());
        }
        //Check the playfield whether it supports selected sport or not
        PlayfieldSport playfieldSport = playfield.getPlayfieldSportList().stream()
                .filter(ps -> ps.getSport() != null && ps.getSport().getId().equals(request.sportId()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Playfield does not support the given sport id: " + request.sportId()));

        Playtime playtime = Playtime.builder()
                .playfieldSport(playfieldSport)
                .playfield(playfield)
                .sport(playfieldSport.getSport())
                .startTime(request.startTime())
                .endTime(request.endTime())
                .maxPlayers(request.maxPlayers())
                .status(PlaytimeStatus.OPEN)
                .participants(new ArrayList<>())
                .build();
        User bookmaker;
        try {
            bookmaker = userService.findUserByUsername(username);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid user provided in username: " + username, e);
        }
        playtime.setBookmaker(bookmaker);

        PlaytimeParticipant bookmakerParticipant = PlaytimeParticipant.builder()
                .playtime(playtime)
                .user(bookmaker)
                .joinedAt(LocalDateTime.now())
                .role(ParticipantRole.BOOKMAKER)
                .build();
        playtime.getParticipants().add(bookmakerParticipant);

        Playtime savedPlaytime = playtimeRepository.save(playtime);
        log.info("PlaytimeService - createPlaytime() - end");
        return mapToPlaytimeResponse(savedPlaytime);
    }

    @Override
    public PlaytimeResponse getPlaytimeById(UUID id) {
        log.info("PlaytimeServiceImpl - getPlaytimeById() - start");
        Playtime playtime = playtimeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Playdate does not exist! id: " + id));
        log.info("PlaytimeService - getPlaytimeById() - end");
        return mapToPlaytimeResponse(playtime);
    }

    @Override
    public Page<PlaytimeResponse> getPlaytimesPageable(int page, int size) {
        log.info("PlaytimeService - getPlaytimesPageable() - start");
        page -= 1;
        if (page < 0 || size < 0) {
            throw new IllegalArgumentException("Page and size parameters must be non-negative");
        }
        Pageable pageable = PageRequest.of(page, size);
        Page<Playtime> playdatePage = playtimeRepository.findAll(pageable);

        List<PlaytimeResponse> responses = playdatePage.getContent().stream()
                .map(this::mapToPlaytimeResponse)
                .collect(Collectors.toList());
        log.info("PlaytimeService - getPlaytimesPageable() - end");
        return new PageImpl<>(responses, pageable, playdatePage.getTotalElements());
    }

    @Override
    public Page<PlaytimeResponse> searchPlaytimes(UUID sportId, String city, String district, String ward, LocalDateTime startDate, LocalDateTime endDate, int page, int size) {
        log.info("PlaytimeService - searchPlaytimes() - start");
        page -= 1;
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("startDate and endDate cannot be null");
        }
        if (page < 0 || size < 0) {
            throw new IllegalArgumentException("Page and size parameters must be non-negative");
        }
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("startDate must be before endDate");
        }

        Pageable pageable = PageRequest.of(page, size);
        Page<Playtime> playtimesPage = playtimeRepository.searchPlaytimes(sportId, city, district, ward, startDate, endDate, pageable);

        if (playtimesPage.isEmpty()) {
            throw new IllegalArgumentException("Cannot find available play time.");
        }

        List<PlaytimeResponse> responses = playtimesPage.getContent().stream()
                .map(this::mapToPlaytimeResponse)
                .collect(Collectors.toList());
        log.info("PlaytimeService - searchPlaytimes() - end");
        return new PageImpl<>(responses, pageable, playtimesPage.getTotalElements());
    }

    @Override
    public PlaytimeResponse joinPlaytime(String username, UUID playtimeId) {
        log.info("PlaytimeService - joinPlaytime() - start");
        Playtime playtime = playtimeRepository.findById(playtimeId)
                .orElseThrow(() -> new RuntimeException("Playtime does not exist with id: " + playtimeId));

        if (playtime.getStatus() != PlaytimeStatus.OPEN) {
            throw new RuntimeException("Playtime is not available to join.");
        }

        if (playtime.getParticipants().size() >= playtime.getMaxPlayers()) {
            throw new RuntimeException("Playtime is full.");
        }

        boolean alreadyJoined = playtime.getParticipants().stream()
                .anyMatch(p -> p.getUser().getUserId().equals(userService.findUserByUsername(username).getUserId()));
        if (alreadyJoined) {
            throw new RuntimeException("User has joined this playtime.");
        }

        User user = userService.findUserByUsername(username);
        PlaytimeParticipant participant = PlaytimeParticipant.builder()
                .playtime(playtime)
                .user(user)
                .joinedAt(LocalDateTime.now())
                .role(ParticipantRole.MEMBER)
                .build();

        playtime.getParticipants().add(participant);

        Playtime updatedPlaytime = playtimeRepository.save(playtime);

        if (updatedPlaytime.getParticipants().size() >= updatedPlaytime.getMaxPlayers()) {
            updatedPlaytime.setStatus(PlaytimeStatus.FULL);
            updatedPlaytime = playtimeRepository.save(updatedPlaytime);
        }

        log.info("PlaytimeService - joinPlaytime() - end");
        return mapToPlaytimeResponse(updatedPlaytime);

    }

    @Override
    public void deletePlaytime(UUID id) {
        log.info("PlaytimeService - deletePlaytime() - start");

        Playtime playtime = playtimeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Playdate does not exist! id: " + id));
        playtime.setStatus(PlaytimeStatus.CLOSED);
        log.info("PlaytimeService - deletePlaytime() - end");

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

    @Override
    public Playtime findPlaytimeById(UUID id) {
        return playtimeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("PlaytimeId", "id", id.toString()));
    }

    @Override
    public List<Playtime> getAllPlaytimesOfUser(String username) {
        User user = userService.findUserByUsername(username);
        return playtimeRepository.findByParticipantsUser(user);
    }
}

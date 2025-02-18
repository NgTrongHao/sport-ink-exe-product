package rubberduck.org.sportinksystemalt.playdate.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rubberduck.org.sportinksystemalt.playdate.domain.dto.CreatePlaydateRequest;
import rubberduck.org.sportinksystemalt.playdate.domain.dto.PlaydateParticipantResponse;
import rubberduck.org.sportinksystemalt.playdate.domain.dto.PlaydateResponse;
import rubberduck.org.sportinksystemalt.playdate.domain.entity.Playdate;
import rubberduck.org.sportinksystemalt.playdate.repository.PlaydateParticipantRepository;
import rubberduck.org.sportinksystemalt.playdate.repository.PlaydateRepository;
import rubberduck.org.sportinksystemalt.playdate.service.PlaydateService;
import rubberduck.org.sportinksystemalt.shared.common.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PlaydateServiceImpl implements PlaydateService {
    private final PlaydateRepository playdateRepository;
    private final PlaydateParticipantRepository playdateParticipantRepository;
    public PlaydateServiceImpl(PlaydateRepository playdateRepository,
                               PlaydateParticipantRepository playdateParticipantRepository) {
        this.playdateRepository = playdateRepository;
        this.playdateParticipantRepository = playdateParticipantRepository;
    }
    @Override
    public PlaydateResponse createPlaydate(CreatePlaydateRequest request) {
        // validate
        if (!request.startTime().isBefore(request.endTime())) {
            throw new IllegalArgumentException("startTime must be before endTime");
        }

        if (request.maxPlayers() <= 0) {
            throw new IllegalArgumentException("maxPlayers must be greater than 0");
        }

        Playdate playdate = Playdate.builder()
                .playfieldSportId(request.playfieldSportId())
                .startTime(request.startTime())
                .endTime(request.endTime())
                .maxPlayers(request.maxPlayers())
                .status("OPEN")
                .participants(new ArrayList<>())
                .build();

        Playdate savedPlaydate = playdateRepository.save(playdate);
        return mapToPlaydateResponse(savedPlaydate);
    }

    @Override
    public PlaydateResponse getPlaydateById(UUID id) {
        Playdate playdate = playdateRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Playdate does not exist! id: " + id));
        return mapToPlaydateResponse(playdate);
    }

    @Override
    public Page<PlaydateResponse> getPlaydatesPageable(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Playdate> playdatePage = playdateRepository.findAll(pageable);

        List<PlaydateResponse> responses = playdatePage.getContent().stream()
                .map(this::mapToPlaydateResponse)
                .collect(Collectors.toList());
        return new PageImpl<>(responses, pageable, playdatePage.getTotalElements());
    }

    @Override
    public void deletePlaydate(UUID id) {
        Playdate playdate = playdateRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Playdate does not exist! id: " + id));
        playdate.setStatus("CLOSED");
        playdateRepository.save(playdate);
    }

    private PlaydateResponse mapToPlaydateResponse(Playdate playdate) {
        List<PlaydateParticipantResponse> participantResponses = playdate.getParticipants() == null
                ? List.of()
                : playdate.getParticipants().stream()
                .map(p -> new PlaydateParticipantResponse(p.getUserId(), p.getJoinedAt()))
                .collect(Collectors.toList());

        return new PlaydateResponse(
                playdate.getId(),
                playdate.getPlayfieldSportId(),
                playdate.getStartTime(),
                playdate.getEndTime(),
                playdate.getMaxPlayers(),
                playdate.getStatus(),
                participantResponses
        );
    }
}

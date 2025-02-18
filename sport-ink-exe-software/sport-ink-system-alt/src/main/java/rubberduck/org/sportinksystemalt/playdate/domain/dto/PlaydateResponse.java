package rubberduck.org.sportinksystemalt.playdate.domain.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record PlaydateResponse(
        UUID id,
        UUID playfieldSportId,
        LocalDateTime startTime,
        LocalDateTime endTime,
        int maxPlayers,
        String status,
        List<PlaydateParticipantResponse> participants
) {}
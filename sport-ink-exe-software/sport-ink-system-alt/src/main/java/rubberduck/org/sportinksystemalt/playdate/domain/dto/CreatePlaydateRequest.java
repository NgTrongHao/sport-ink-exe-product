package rubberduck.org.sportinksystemalt.playdate.domain.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record CreatePlaydateRequest(
        UUID playfieldSportId,
        LocalDateTime startTime,
        LocalDateTime endTime,
        int maxPlayers
) {}
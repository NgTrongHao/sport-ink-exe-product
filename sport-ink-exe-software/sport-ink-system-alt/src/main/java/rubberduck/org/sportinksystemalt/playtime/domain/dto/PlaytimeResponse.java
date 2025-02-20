package rubberduck.org.sportinksystemalt.playtime.domain.dto;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record PlaytimeResponse(
        @NotNull UUID id,
        @NotNull UUID playfieldSportId,
        @NotNull UUID playfieldId,
        @NotNull UUID sportId,
        @NotNull UUID bookmakerId,
        @NotNull LocalDateTime startTime,
        @NotNull LocalDateTime endTime,
        @Positive int maxPlayers,
        @NotEmpty String status,
        @NotNull List<PlaytimeParticipantResponse> participants
) {}
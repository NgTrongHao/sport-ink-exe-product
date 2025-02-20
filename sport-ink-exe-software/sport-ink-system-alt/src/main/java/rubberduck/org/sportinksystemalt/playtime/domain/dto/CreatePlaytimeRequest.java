package rubberduck.org.sportinksystemalt.playtime.domain.dto;

import com.google.firebase.database.annotations.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;
import java.util.UUID;

public record CreatePlaytimeRequest(
        @NotNull UUID playfieldId,
        @NotNull UUID sportId,
        @NotNull LocalDateTime startTime,
        @NotNull LocalDateTime endTime,
        @Positive int maxPlayers

) {}
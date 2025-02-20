    package rubberduck.org.sportinksystemalt.playtime.domain.dto;

    import com.google.firebase.database.annotations.NotNull;
    import rubberduck.org.sportinksystemalt.playtime.domain.entity.ParticipantRole;

    import java.time.LocalDateTime;
    import java.util.UUID;

    public record PlaytimeParticipantResponse(
            @NotNull UUID userId,
            @NotNull LocalDateTime joinedAt,
            @NotNull ParticipantRole role

    ){}
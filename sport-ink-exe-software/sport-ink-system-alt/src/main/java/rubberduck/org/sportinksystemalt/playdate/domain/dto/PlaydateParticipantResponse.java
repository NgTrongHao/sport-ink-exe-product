package rubberduck.org.sportinksystemalt.playdate.domain.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record PlaydateParticipantResponse (
         UUID userId,
         LocalDateTime joinedAt

){}
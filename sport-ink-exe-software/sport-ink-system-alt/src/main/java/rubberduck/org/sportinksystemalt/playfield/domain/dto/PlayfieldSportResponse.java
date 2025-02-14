package rubberduck.org.sportinksystemalt.playfield.domain.dto;

import rubberduck.org.sportinksystemalt.administration.domain.dto.SportResponse;

public record PlayfieldSportResponse(
        SportResponse sport,
        boolean enabled
) {
}

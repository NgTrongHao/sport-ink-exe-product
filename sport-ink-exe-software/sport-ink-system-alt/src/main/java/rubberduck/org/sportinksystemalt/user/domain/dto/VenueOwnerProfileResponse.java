package rubberduck.org.sportinksystemalt.user.domain.dto;

import lombok.Builder;

import java.io.Serializable;

@Builder
public record VenueOwnerProfileResponse(
        String taxCode
) implements Serializable {
}

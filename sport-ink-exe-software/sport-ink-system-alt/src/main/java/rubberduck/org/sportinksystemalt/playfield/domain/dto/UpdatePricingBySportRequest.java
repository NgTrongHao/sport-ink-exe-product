package rubberduck.org.sportinksystemalt.playfield.domain.dto;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

public record UpdatePricingBySportRequest(
        UUID playfieldId,
        UUID sportId,
        List<PricingRule> pricingRules
) {
    public record PricingRule(
            DayOfWeek dayOfWeek,
            LocalTime startTime,
            LocalTime endTime,
            Double pricePerHour,
            Double pricePerHalfHour,
            String currency
    ) {
    }
}


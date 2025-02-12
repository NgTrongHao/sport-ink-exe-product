package rubberduck.org.sportinksystemalt.playfield.domain.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

import java.time.LocalTime;
import java.util.List;

public record CreateVenueLocationRequest(
        String name,

        @NotEmpty
        String address,

        @NotEmpty
        @Pattern(regexp = "^[-+]?([1-8]?\\d(\\.\\d+)?|90(\\.0+)?)$")
        Double latitude,

        @NotEmpty
        @Pattern(regexp = "^[-+]?((1[0-7]\\d(\\.\\d+)?)|(180(\\.0+)?))$")
        Double longitude,
        String description,
        List<String> imageUrls,

        @NotEmpty
        String phoneContact,

        LocalTime opening,

        LocalTime closing
) {
}

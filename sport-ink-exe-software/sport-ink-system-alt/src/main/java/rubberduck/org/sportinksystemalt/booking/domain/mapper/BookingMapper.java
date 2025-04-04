package rubberduck.org.sportinksystemalt.booking.domain.mapper;

import rubberduck.org.sportinksystemalt.administration.domain.dto.SportResponse;
import rubberduck.org.sportinksystemalt.booking.domain.dto.BookingResponse;
import rubberduck.org.sportinksystemalt.booking.domain.entity.Booking;
import rubberduck.org.sportinksystemalt.playfield.domain.dto.OpeningHoursDTO;
import rubberduck.org.sportinksystemalt.playfield.domain.dto.PlayfieldResponse;
import rubberduck.org.sportinksystemalt.playfield.domain.dto.PlayfieldSportResponse;
import rubberduck.org.sportinksystemalt.playfield.domain.dto.VenueLocationResponse;
import rubberduck.org.sportinksystemalt.playfield.domain.entity.OpeningHours;
import rubberduck.org.sportinksystemalt.playfield.domain.entity.Playfield;
import rubberduck.org.sportinksystemalt.playfield.domain.entity.PlayfieldSport;

import java.util.stream.Collectors;

public class BookingMapper {
    public static BookingResponse toBookingResponse(Booking booking) {
        return new BookingResponse(
                booking.getBookingId(),
                booking.getStartDateTime(),
                booking.getEndDateTime(),
                booking.getBookingPrice(),
                convertToPlayfieldResponse(booking.getBookingPlayfield()),
                convertToVenueLocationResponse(booking.getBookingPlayfield()),
                booking.getBookingCode(),
                booking.getBookingStatus(),
                booking.getBookingNote(),
                convertToSportResponse(booking)
        );
    }

    private static SportResponse convertToSportResponse(Booking booking) {
        return new SportResponse(
                booking.getBookingSport().getId(),
                booking.getBookingSport().getName(),
                booking.getBookingSport().getDescription(),
                booking.getBookingSport().getIconUrl()
        );
    }

    private static VenueLocationResponse convertToVenueLocationResponse(Playfield playfield) {
        return new VenueLocationResponse(
                playfield.getVenueLocation().getId(),
                playfield.getVenueLocation().getName(),
                playfield.getVenueLocation().getAddress(),
                playfield.getVenueLocation().getWard(),
                playfield.getVenueLocation().getDistrict(),
                playfield.getVenueLocation().getCity(),
                playfield.getVenueLocation().getLatitude(),
                playfield.getVenueLocation().getLongitude(),
                playfield.getVenueLocation().getDescription(),
                playfield.getVenueLocation().getImageUrls(),
                playfield.getVenueLocation().getPhoneContact(),
                playfield.getVenueLocation().getOpeningHoursList().stream()
                        .map(BookingMapper::convertToOpeningHoursDTO)
                        .collect(Collectors.toList())
        );
    }

    private static PlayfieldResponse convertToPlayfieldResponse(Playfield playfield) {
        return new PlayfieldResponse(
                playfield.getId(),
                playfield.getName(),
                playfield.getDescription(),
                playfield.getImageUrls(),
                playfield.isEnabled(),
                playfield.getPlayfieldSportList().stream()
                        .map(BookingMapper::convertToPlayfieldSportResponse)
                        .collect(Collectors.toList())
        );
    }

    private static PlayfieldSportResponse convertToPlayfieldSportResponse(PlayfieldSport playfieldSport) {
        SportResponse sportResponse = new SportResponse(
                playfieldSport.getSport().getId(),
                playfieldSport.getSport().getName(),
                playfieldSport.getSport().getDescription(),
                playfieldSport.getSport().getIconUrl()
        );
        return new PlayfieldSportResponse(sportResponse, playfieldSport.isEnabled());
    }

    private static OpeningHoursDTO convertToOpeningHoursDTO(OpeningHours openingHours) {
        return new OpeningHoursDTO(
                openingHours.getDayOfWeek(),
                openingHours.getOpeningTime(),
                openingHours.getClosingTime()
        );
    }
}

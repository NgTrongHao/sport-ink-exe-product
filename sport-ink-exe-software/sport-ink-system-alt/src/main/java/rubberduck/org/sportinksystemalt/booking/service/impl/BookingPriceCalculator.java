package rubberduck.org.sportinksystemalt.booking.service.impl;

import org.springframework.stereotype.Component;
import rubberduck.org.sportinksystemalt.playfield.domain.entity.PlayfieldPricing;
import rubberduck.org.sportinksystemalt.playfield.domain.entity.PlayfieldSport;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

@Component
public class BookingPriceCalculator {

    public Double calculate(PlayfieldSport playfieldSport, LocalTime startTime, int duration, LocalDate bookingDate) {
        double totalPrice = 0.0;
        LocalTime endTime = startTime.plusMinutes(duration);

        for (PlayfieldPricing pricing : playfieldSport.getPricingRules()) {
            if (pricing.getDayOfWeek().equals(bookingDate.getDayOfWeek())) {
                if (isTimeOverlap(startTime, endTime, pricing)) {
                    totalPrice += calculatePriceForTimeSlot(startTime, endTime, pricing);
                }
            }
        }

        return totalPrice;
    }

    private boolean isTimeOverlap(LocalTime startTime, LocalTime endTime, PlayfieldPricing pricing) {
        LocalTime pricingStartTime = pricing.getStartTime();
        LocalTime pricingEndTime = pricing.getEndTime();

        return !(endTime.isBefore(pricingStartTime) || startTime.isAfter(pricingEndTime));
    }

    private double calculatePriceForTimeSlot(LocalTime startTime, LocalTime endTime, PlayfieldPricing pricing) {
        LocalTime pricingStartTime = pricing.getStartTime();
        LocalTime pricingEndTime = pricing.getEndTime();

        LocalTime effectiveStartTime = startTime.isBefore(pricingStartTime) ? pricingStartTime : startTime;
        LocalTime effectiveEndTime = endTime.isAfter(pricingEndTime) ? pricingEndTime : endTime;

        if (effectiveStartTime.equals(effectiveEndTime) || effectiveStartTime.isAfter(effectiveEndTime)) {
            return 0;
        }

        long minutesInSlot = Duration.between(effectiveStartTime, effectiveEndTime).toMinutes();

        if (minutesInSlot <= 30) {
            return pricing.getPricePerHalfHour();
        } else {
            long hours = minutesInSlot / 60;
            long remainingMinutes = minutesInSlot % 60;

            double totalPrice = 0;
            if (remainingMinutes > 0) {
                totalPrice = pricing.getPricePerHour() * hours + pricing.getPricePerHalfHour();
            } else {
                totalPrice = pricing.getPricePerHour() * hours;
            }

            return totalPrice;
        }
    }
}

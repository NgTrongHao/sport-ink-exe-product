package rubberduck.org.sportinksystemalt.shared.domain;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ErrorDetail(LocalDateTime timestamp, String message, String details) {
    public ErrorDetail {
        if (message == null || message.isBlank()) {
            throw new IllegalArgumentException("Message cannot be null or empty");
        }
        if (details == null || details.isBlank()) {
            throw new IllegalArgumentException("Details cannot be null or empty");
        }
    }
}

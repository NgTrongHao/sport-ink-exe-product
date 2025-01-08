package rubberduck.org.sportinksystem.core.domain.valueObject;

import java.util.regex.Pattern;

public record Email(String email) {

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$");

    public Email {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            throw new IllegalArgumentException("Invalid email format");
        }
    }

}

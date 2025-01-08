package rubberduck.org.sportinksystem.core.domain.valueObject;

import java.util.regex.Pattern;

public record Username(String username) {

    private static final Pattern USERNAME_PATTERN = Pattern.compile("^[a-zA-Z0-9]{3,20}$");

    public Username {
        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("Username cannot be null");
        }
        if (!USERNAME_PATTERN.matcher(username).matches()) {
            throw new IllegalArgumentException("Invalid username");
        }
    }

}

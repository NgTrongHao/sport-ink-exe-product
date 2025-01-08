package rubberduck.org.sportinksystem.core.domain.entity.token;

import java.time.LocalDateTime;

public abstract class BaseToken {
    protected final String token;
    protected final String username;

    protected BaseToken(String token, String username) {
        this.token = token;
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public String getUsername() {
        return username;
    }
}

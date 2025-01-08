package rubberduck.org.sportinksystem.core.domain.entity.token;

import java.time.LocalDateTime;

public class RefreshToken extends BaseToken {
    protected RefreshToken(String token, String username) {
        super(token, username);
    }

    public static RefreshToken create(String token, String username) {
        return new RefreshToken(token, username);
    }
}

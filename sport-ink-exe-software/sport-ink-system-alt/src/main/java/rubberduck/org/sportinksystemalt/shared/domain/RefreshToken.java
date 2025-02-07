package rubberduck.org.sportinksystemalt.shared.domain;

public class RefreshToken extends BaseToken {
    protected RefreshToken(String token, String username) {
        super(token, username, TokenType.REFRESH_TOKEN);
    }

    public static RefreshToken create(String token, String username) {
        return new RefreshToken(token, username);
    }
}

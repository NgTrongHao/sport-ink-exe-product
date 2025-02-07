package rubberduck.org.sportinksystemalt.shared.domain;

public abstract class BaseToken {
    protected final String token;
    protected final String username;
    protected final TokenType type;

    protected BaseToken(String token, String username, TokenType type) {
        this.token = token;
        this.username = username;
        this.type = type;
    }

    public String getToken() {
        return token;
    }

    public String getUsername() {
        return username;
    }

    public TokenType getType() {
        return type;
    }
}


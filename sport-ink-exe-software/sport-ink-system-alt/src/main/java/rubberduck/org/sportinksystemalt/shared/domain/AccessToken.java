package rubberduck.org.sportinksystemalt.shared.domain;

import lombok.Getter;

import java.util.Set;

@Getter
public class AccessToken extends BaseToken {
    private final Set<String> scopes;

    public static AccessToken create(String token, String username, Set<String> scopes) {
        return new AccessToken(token, username, scopes);
    }

    protected AccessToken(String token, String username, Set<String> scopes) {
        super(token, username, TokenType.ACCESS_TOKEN);
        this.scopes = scopes;
    }

}

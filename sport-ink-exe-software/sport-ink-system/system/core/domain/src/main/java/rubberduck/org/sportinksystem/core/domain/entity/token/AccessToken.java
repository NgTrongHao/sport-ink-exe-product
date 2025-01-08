package rubberduck.org.sportinksystem.core.domain.entity.token;

import java.util.Set;

public class AccessToken extends BaseToken {
    private final Set<String> scopes;

    public static AccessToken create(String token, String username, Set<String> scopes) {
        return new AccessToken(token, username, scopes);
    }

    protected AccessToken(String token, String username, Set<String> scopes) {
        super(token, username);
        this.scopes = scopes;
    }

    public Set<String> getScopes() {
        return scopes;
    }
}

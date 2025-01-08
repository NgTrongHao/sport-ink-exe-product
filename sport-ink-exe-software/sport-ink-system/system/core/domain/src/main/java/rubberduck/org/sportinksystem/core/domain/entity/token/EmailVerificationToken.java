package rubberduck.org.sportinksystem.core.domain.entity.token;

public class EmailVerificationToken extends BaseToken {
    protected EmailVerificationToken(String token, String username) {
        super(token, username);
    }

    public static EmailVerificationToken create(String token, String username) {
        return new EmailVerificationToken(token, username);
    }
}

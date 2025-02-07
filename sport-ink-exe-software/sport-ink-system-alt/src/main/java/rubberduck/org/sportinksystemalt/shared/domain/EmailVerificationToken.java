package rubberduck.org.sportinksystemalt.shared.domain;

public class EmailVerificationToken extends BaseToken {
    protected EmailVerificationToken(String token, String username) {
        super(token, username, TokenType.EMAIL_VERIFICATION_TOKEN);
    }

    public static EmailVerificationToken create(String token, String username) {
        return new EmailVerificationToken(token, username);
    }
}

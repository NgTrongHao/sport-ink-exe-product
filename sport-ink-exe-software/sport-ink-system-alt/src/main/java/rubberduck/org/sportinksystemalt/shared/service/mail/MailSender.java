package rubberduck.org.sportinksystemalt.shared.service.mail;

public interface MailSender {
    void sendWelcomeEmail(String email, String name);

    void sendEmailVerificationEmail(String email, String name, String token);

    void sendPasswordResetEmail(String email, String name, String token);
}

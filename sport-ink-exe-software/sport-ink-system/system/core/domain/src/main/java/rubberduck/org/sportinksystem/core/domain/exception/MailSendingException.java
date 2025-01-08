package rubberduck.org.sportinksystem.core.domain.exception;

public class MailSendingException extends RuntimeException {
    public MailSendingException(String message, Throwable cause) {
        super(message, cause);
    }
}

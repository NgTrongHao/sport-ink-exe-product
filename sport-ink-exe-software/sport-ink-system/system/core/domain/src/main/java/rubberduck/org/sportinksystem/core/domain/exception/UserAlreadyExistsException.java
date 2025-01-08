package rubberduck.org.sportinksystem.core.domain.exception;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}

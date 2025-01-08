package rubberduck.org.sportinksystem.core.applicationservice.queue;

public interface MessageEncryptor {
    String encrypt(String message) throws Exception;

    String decrypt(String message) throws Exception;
}

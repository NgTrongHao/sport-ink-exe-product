package rubberduck.org.sportinksystemalt.shared.service.queue;

public interface MessageEncryptor {
    String encrypt(String message) throws Exception;

    String decrypt(String message) throws Exception;
}

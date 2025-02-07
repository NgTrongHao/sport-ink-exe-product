package rubberduck.org.sportinksystemalt.shared.common.service.queue;

import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.util.Base64;

@Component
public class AesMessageEncryptor implements MessageEncryptor {
    private static final String ALGORITHM = "AES";
    private final SecretKey secretKey;

    public AesMessageEncryptor() throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance(ALGORITHM);
        keyGen.init(128); // AES 128-bit
        this.secretKey = keyGen.generateKey();
    }

    /**
     * Encrypts a message using AES algorithm.
     *
     * @param message the message to encrypt
     * @return the encrypted message
     * @throws Exception if an error occurs during encryption
     */
    public String encrypt(String message) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encrypted = cipher.doFinal(message.getBytes());
        return Base64.getEncoder().encodeToString(encrypted);
    }

    /**
     * Decrypts an encrypted message using AES algorithm.
     *
     * @param encryptedMessage the encrypted message to decrypt
     * @return the decrypted message
     * @throws Exception if an error occurs during decryption
     */
    public String decrypt(String encryptedMessage) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decoded = Base64.getDecoder().decode(encryptedMessage);
        return new String(cipher.doFinal(decoded));
    }
}
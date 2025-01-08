package rubberduck.org.sportinksystem.core.applicationservice.hashing;

public interface PasswordHasher {
    String hash(String password);

    boolean matches(String password, String hashedPassword);
}

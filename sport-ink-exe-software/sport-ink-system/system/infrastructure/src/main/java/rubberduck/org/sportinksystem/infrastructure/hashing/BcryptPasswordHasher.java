package rubberduck.org.sportinksystem.infrastructure.hashing;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;
import rubberduck.org.sportinksystem.core.applicationservice.hashing.PasswordHasher;

@Component
public class BcryptPasswordHasher implements PasswordHasher {

    @Override
    public String hash(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    @Override
    public boolean matches(String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }
}

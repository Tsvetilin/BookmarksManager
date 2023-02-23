package bg.sofia.uni.fmi.mjt.bookmarks.server.services.hasher;

import bg.sofia.uni.fmi.mjt.bookmarks.server.exceptions.PasswordHasherException;
import bg.sofia.uni.fmi.mjt.bookmarks.server.services.hasher.strategy.HasherAlgorithmStrategy;
import bg.sofia.uni.fmi.mjt.bookmarks.server.utils.SecureString;

public class DefaultPasswordHasher implements PasswordHasher {
    private final HasherAlgorithmStrategy strategy;

    public DefaultPasswordHasher(HasherAlgorithmStrategy strategy) {
        this.strategy = strategy;
    }

    @Override
    public boolean verify(SecureString password, String expectedHash) throws PasswordHasherException {
        return strategy.verify(password, expectedHash);
    }

    @Override
    public String hash(SecureString password) throws PasswordHasherException {
        return strategy.hash(password);
    }
}


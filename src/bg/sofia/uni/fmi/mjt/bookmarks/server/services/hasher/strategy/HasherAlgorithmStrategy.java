package bg.sofia.uni.fmi.mjt.bookmarks.server.services.hasher.strategy;

import bg.sofia.uni.fmi.mjt.bookmarks.server.exceptions.PasswordHasherException;
import bg.sofia.uni.fmi.mjt.bookmarks.server.utils.SecureString;

public interface HasherAlgorithmStrategy {
    boolean verify(SecureString password, String expectedHash) throws PasswordHasherException;

    String hash(SecureString password) throws PasswordHasherException;
}

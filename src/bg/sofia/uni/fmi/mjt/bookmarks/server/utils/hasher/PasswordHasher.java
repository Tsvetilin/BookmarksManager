package bg.sofia.uni.fmi.mjt.bookmarks.server.utils.hasher;

import bg.sofia.uni.fmi.mjt.bookmarks.server.utils.Service;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public interface PasswordHasher extends Service {

    boolean verify(String password, String expectedHash) throws NoSuchAlgorithmException, InvalidKeySpecException;

    String hash(String password) throws InvalidKeySpecException, NoSuchAlgorithmException;
}

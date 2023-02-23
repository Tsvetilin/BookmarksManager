package bg.sofia.uni.fmi.mjt.bookmarks.server.services.password;

import bg.sofia.uni.fmi.mjt.bookmarks.server.utils.SecureString;

public interface PasswordValidatorService {
    boolean validate(SecureString password);

    String getPasswordRequirements();
}

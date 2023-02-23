package bg.sofia.uni.fmi.mjt.bookmarks.server.services.password;

import bg.sofia.uni.fmi.mjt.bookmarks.server.utils.SecureString;

import java.nio.CharBuffer;
import java.util.regex.Pattern;

public class DefaultPasswordValidatorService implements PasswordValidatorService {
    // TODO: Add to service / strategy
    private static final String PASSWORD_REGEX = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!.dsa@$%^&*-]).{8,}$";
    private static final Pattern PASSWORD_PATTERN = Pattern.compile(PASSWORD_REGEX);
    private static final String PASSWORD_REQUIREMENTS =
        "Password must be at least 8 symbols long, contain 1 uppercase letter, " +
            "1 lowercase letter, 1 number, 1 non-alphanumeric character.";

    @Override
    public boolean validate(SecureString password) {
        return PASSWORD_PATTERN.matcher(CharBuffer.wrap(password.toCharArray())).matches();
    }

    @Override
    public String getPasswordRequirements() {
        return PASSWORD_REQUIREMENTS;
    }
}

package springboot.v2.security.example.user;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CustomPasswordEncoder implements PasswordEncoder {

    private String salt = "salt";

    @Override
    public String encode(CharSequence rawPassword) {
        return calculatePasswordHash(rawPassword.toString());
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        String calculated = calculatePasswordHash(rawPassword.toString());
        if (calculated == null || encodedPassword == null) {
            return false;
        }
        return calculated.equals(encodedPassword);
    }

    private String calculatePasswordHash(String password) {
        try {
            return DigestUtils.md5Hex(String.format("%s%s", password, salt)).toUpperCase();
        } catch (Exception e) {
            return null;
        }
    }

}

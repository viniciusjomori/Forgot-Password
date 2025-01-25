package br.viniciusjomori.forgotpassword.Security.Util;

import java.util.regex.Pattern;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordUtil extends BCryptPasswordEncoder {

    private static final String PASSWORD_REGEX = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[\\W_]).{8,}$";
    
    @Override
    public String encode(CharSequence rawPassword) {
        if (rawPassword == null || rawPassword.isEmpty()) return null;

        return super.encode(rawPassword);
    }
    
    public boolean matches(String rawPassword, UserDetails user) {
        return super.matches(rawPassword, user.getPassword());
    }

    public boolean isValid(String password) {
        if (password == null) {
            return false;
        }
        return Pattern.matches(PASSWORD_REGEX, password);
    }

}


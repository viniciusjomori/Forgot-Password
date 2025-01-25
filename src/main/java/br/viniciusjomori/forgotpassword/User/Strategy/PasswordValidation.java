package br.viniciusjomori.forgotpassword.User.Strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.viniciusjomori.forgotpassword.Security.Util.PasswordUtil;
import br.viniciusjomori.forgotpassword.User.UserEntity;

@Component
public class PasswordValidation implements IUserValidation {

    private String message = null;

    @Autowired
    private PasswordUtil passwordUtil;

    @Override
    public boolean validate(UserEntity user) {
        String password = user.getPassword();

        if (password == null || password.isEmpty() || password.isBlank()) {
            this.message = "Password is null";
            return false;
        };

        if (!passwordUtil.isValid(password)) {
            this.message = "Weak password";
            return false;
        }

        return true;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
    
}

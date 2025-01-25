package br.viniciusjomori.forgotpassword.User.Strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.viniciusjomori.forgotpassword.User.UserEntity;
import br.viniciusjomori.forgotpassword.User.UserRepository;

@Component
public class EmailValidation implements IUserValidation {

    @Autowired
    private UserRepository repository;

    private String message = null;

    @Override
    public boolean validate(UserEntity user) {
        String email = user.getEmail();

        if (email == null || email.isEmpty() || email.isBlank()) {
            this.message = "Email is null";
            return false;
        }

        if (repository.findByEmail(email).isPresent()) {
            this.message = "Email already registred";
            return false;
        }
        
        return true;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
    
}

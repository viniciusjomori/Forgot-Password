package br.viniciusjomori.forgotpassword.User.Strategy;

import br.viniciusjomori.forgotpassword.User.UserEntity;

public interface IUserValidation {

    public boolean validate(UserEntity user);

    public String getMessage();
    
}

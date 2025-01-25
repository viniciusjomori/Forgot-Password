package br.viniciusjomori.forgotpassword.User;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.viniciusjomori.forgotpassword.App.Exception.BadRequestException;
import br.viniciusjomori.forgotpassword.Security.Util.PasswordUtil;
import br.viniciusjomori.forgotpassword.User.DTO.UserRegisterDTO;
import br.viniciusjomori.forgotpassword.User.Strategy.IUserValidation;

@Service
public class UserService {
    
    @Autowired
    private UserRepository repository;

    @Autowired
    private UserMapper mapper;

    @Autowired
    private PasswordUtil passwordUtil;

    @Autowired
    private List<IUserValidation> validations;

    public UserEntity registerUser(UserRegisterDTO dto) {
        UserEntity entity = mapper.toEntity(dto);
        validateUser(entity);

        String password = passwordUtil.encode(dto.password());
        entity.setPassword(password);

        return repository.save(entity);

    }

    private void validateUser(UserEntity user) {
        List<String> errors = new ArrayList<>();

        for (IUserValidation validation : validations) {
            if(!validation.validate(user))
                errors.add(validation.getMessage());
        }

        if (!errors.isEmpty())
            throw new BadRequestException(errors);
    }
}
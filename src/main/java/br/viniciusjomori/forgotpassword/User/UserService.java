package br.viniciusjomori.forgotpassword.User;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import br.viniciusjomori.forgotpassword.App.Exception.BadRequestException;
import br.viniciusjomori.forgotpassword.App.Exception.NotFoundException;
import br.viniciusjomori.forgotpassword.ForgotPassword.ForgotPasswordEntity;
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

    public UserEntity getMyUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof UserEntity) {
            long id = ((UserEntity) principal).getId();
            return repository.findById(id).get();
        }
        else return null;
    }

    public UserEntity registerUser(UserRegisterDTO dto) {
        UserEntity entity = mapper.toEntity(dto);
        validateUser(entity);

        String password = passwordUtil.encode(dto.password());
        entity.setPassword(password);

        return repository.save(entity);

    }

    public UserEntity findByEmail(String email) {
        return repository.findByEmail(email)
            .orElseThrow(() -> new NotFoundException(email + "not found"));
    }

    public UserEntity updatePassword(ForgotPasswordEntity request, String password) {
        String errorMessage = null;
        UserEntity user = request.getUser();
        
        if (!request.getActive())
            errorMessage = "Reset password request is not active";
        else if(!passwordUtil.isValid(password)) {
            errorMessage = "Weak password";
        }
        else if(passwordUtil.matches(password, user))
            errorMessage = "New password can't be the current password";

        if (errorMessage != null) {
            throw new BadRequestException(errorMessage);
        }

        String hash = passwordUtil.encode(password);
        user.setPassword(hash);

        return repository.save(user);
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
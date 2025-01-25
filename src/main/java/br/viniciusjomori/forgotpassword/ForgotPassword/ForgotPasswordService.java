package br.viniciusjomori.forgotpassword.ForgotPassword;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.viniciusjomori.forgotpassword.App.Exception.NotFoundException;
import br.viniciusjomori.forgotpassword.Email.EmailService;
import br.viniciusjomori.forgotpassword.ForgotPassword.DTO.NewPasswordDTO;
import br.viniciusjomori.forgotpassword.User.UserEntity;
import br.viniciusjomori.forgotpassword.User.UserService;

@Service
public class ForgotPasswordService {
    
    @Autowired
    private ForgotPasswordRepository repository;

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    public ForgotPasswordEntity forgotPassword(String email) {
        UserEntity user = userService.findByEmail(email);

        ForgotPasswordEntity entity = ForgotPasswordEntity.builder()
            .user(user)
            .expirationDate(LocalDateTime.now().minusHours(1))
            .build();
        
        entity = repository.save(entity);
        sendEmail(entity);

        return entity;
    }

    public void updatePassword(long id, NewPasswordDTO dto) {
        ForgotPasswordEntity entity = findById(id);
        userService.updatePassword(entity, dto.newPassword());

        entity.setActive(false);
        repository.save(entity);
    }

    private ForgotPasswordEntity findById(long id) {
        return repository.findById(id)
            .orElseThrow(() -> new NotFoundException("Reset password request not found"));
    }

    private void sendEmail(ForgotPasswordEntity entity) {
        String addressTo = entity.getUser().getEmail();
        String subject = "Reset Password";
        String content = "formulario";

        emailService.sendEmail(addressTo, subject, content);
    }
}

package br.viniciusjomori.forgotpassword.ForgotPassword;

import java.time.LocalDateTime;
import java.util.Collection;

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

        desactiveRequests(user);

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

    private void desactiveRequests(UserEntity user) {
        Collection<ForgotPasswordEntity> requests = repository.findActiveByUser(user);
        for (ForgotPasswordEntity request : requests) {
            request.setActive(false);
        }
        repository.saveAll(requests);
    }

    private ForgotPasswordEntity findById(long id) {
        return repository.findById(id)
            .orElseThrow(() -> new NotFoundException("Reset password request not found"));
    }

    private void sendEmail(ForgotPasswordEntity entity) {
        String addressTo = entity.getUser().getEmail();
        String subject = "Reset Password";
        
        StringBuilder sb = new StringBuilder();
        sb.append("<h2>Reset de Senha</h2>");
        sb.append(String.format(
            "<p>http://localhost:8080/forgot-password/%s</p>",
            entity.getId()
        ));
        String content = sb.toString();

        emailService.sendEmail(addressTo, subject, content);
    }
}

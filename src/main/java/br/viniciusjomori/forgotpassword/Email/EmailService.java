package br.viniciusjomori.forgotpassword.Email;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {
    
    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private EmailRepository repository;

    @Value("${spring.mail.username}")
    private String fromAddress;

    public EmailEntity sendEmail(String toAddress, String subject, String content) {
        EmailEntity entity = EmailEntity.builder()
            .fromAddress(fromAddress)
            .toAddress(toAddress)
            .subject(subject)
            .content(content)
            .sendDate(LocalDateTime.now())
            .build();
        
        try {
            sendEmail(entity);
            entity.setStatus(EmailStatus.SENT);
        } catch (MessagingException me) {
            entity.setStatus(EmailStatus.ERROR);
        } 
        
        return repository.save(entity);
    }

    private void sendEmail(EmailEntity entity) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        
        helper.setFrom(entity.getFromAddress());
        helper.setTo(entity.getToAddress());
        helper.setSubject(entity.getSubject());
        helper.setText(entity.getContent(), true); // 'true' para conteúdo em HTML
        
        javaMailSender.send(message);
    }

}

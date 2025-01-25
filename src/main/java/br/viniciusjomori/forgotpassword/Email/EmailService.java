package br.viniciusjomori.forgotpassword.Email;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

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
        } catch (MailException me) {
            entity.setStatus(EmailStatus.ERROR);
        } 
        
        return repository.save(entity);
    }

    private void sendEmail(EmailEntity entity) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(entity.getFromAddress());
        message.setTo(entity.getToAddress());
        message.setSubject(entity.getSubject());
        message.setText(entity.getContent());
        javaMailSender.send(message);

    }

}

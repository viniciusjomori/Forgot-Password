package br.viniciusjomori.forgotpassword.Email;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailRepository extends JpaRepository<EmailEntity, Long> {
    
}

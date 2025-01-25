package br.viniciusjomori.forgotpassword.ForgotPassword;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ForgotPasswordRepository extends JpaRepository<ForgotPasswordEntity, Long> {
    
}

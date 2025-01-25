package br.viniciusjomori.forgotpassword.ForgotPassword;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.viniciusjomori.forgotpassword.User.UserEntity;

public interface ForgotPasswordRepository extends JpaRepository<ForgotPasswordEntity, Long> {
    
    @Query("SELECT t FROM ForgotPasswordEntity t WHERE t.user = :user AND t.active = true")
    Collection<ForgotPasswordEntity> findActiveByUser(UserEntity user);

}

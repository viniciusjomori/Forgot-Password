package br.viniciusjomori.forgotpassword.ForgotPassword;

import java.time.LocalDateTime;

import br.viniciusjomori.forgotpassword.App.BaseEntity;
import br.viniciusjomori.forgotpassword.User.UserEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "forgot_password")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ForgotPasswordEntity extends BaseEntity {
    
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Column
    private LocalDateTime expirationDate;

    public Boolean getActive() {
        return super.getActive() && this.getExpirationDate().isBefore(LocalDateTime.now());
    }

}

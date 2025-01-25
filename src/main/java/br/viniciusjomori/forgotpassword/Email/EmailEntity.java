package br.viniciusjomori.forgotpassword.Email;

import java.time.LocalDateTime;

import br.viniciusjomori.forgotpassword.App.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "emails")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class EmailEntity extends BaseEntity {
    
    @Column
    private String fromAddress;

    @Column
    private String toAddress;

    @Column
    private String subject;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime sendDate;

    @Column
    @Enumerated(EnumType.STRING)
    private EmailStatus status;



}

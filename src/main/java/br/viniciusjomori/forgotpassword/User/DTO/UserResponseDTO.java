package br.viniciusjomori.forgotpassword.User.DTO;

import java.time.LocalDateTime;

public record UserResponseDTO(
    boolean active,
    String email,
    LocalDateTime createDate,
    LocalDateTime editDate
) {
    
}

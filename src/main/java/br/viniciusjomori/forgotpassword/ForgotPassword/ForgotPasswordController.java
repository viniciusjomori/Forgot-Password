package br.viniciusjomori.forgotpassword.ForgotPassword;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.viniciusjomori.forgotpassword.App.ResponseDTO;
import br.viniciusjomori.forgotpassword.ForgotPassword.DTO.NewPasswordDTO;
import jakarta.websocket.server.PathParam;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/forgot-password")
public class ForgotPasswordController {

    @Autowired
    private ForgotPasswordService service;
    
    @PostMapping
    public ResponseEntity<ResponseDTO> forgotPassword(@PathParam("email") String email) {
        service.forgotPassword(email);
        ResponseDTO response = new ResponseDTO(
            HttpStatus.OK,
            "Reset password sent to " + email
        );

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO> updatePassword(
        @PathVariable long id,
        @RequestBody NewPasswordDTO dto)
    {
        service.updatePassword(id, dto);

        ResponseDTO response = new ResponseDTO(
            HttpStatus.OK,
            "Password reset"
        );

        return ResponseEntity.ok(response);

    }


}

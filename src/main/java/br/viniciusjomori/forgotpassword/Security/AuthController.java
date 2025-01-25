package br.viniciusjomori.forgotpassword.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.viniciusjomori.forgotpassword.App.ResponseDTO;
import br.viniciusjomori.forgotpassword.Security.DTO.LoginRequestDTO;
import br.viniciusjomori.forgotpassword.Security.DTO.TokenResponseDTO;
import br.viniciusjomori.forgotpassword.User.UserEntity;
import br.viniciusjomori.forgotpassword.User.UserService;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;

@RestController
@RequestMapping("auth")
@CrossOrigin(origins = "*")
public class AuthController {
    
    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;

    @Autowired
    private ResponseDTO responseDTO;

    @PostMapping("/login")
    @PermitAll
    public ResponseEntity<TokenResponseDTO> authenticate(@RequestBody @Valid LoginRequestDTO login) {
        return ResponseEntity.ok(authService.authenticate(login));
    }

    @PostMapping("logout")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ResponseDTO> logout() {

        UserEntity user = userService.getMyUser();
        authService.revokeAllTokens(user);
        responseDTO.setHttpStatus(HttpStatus.OK);
        responseDTO.setMessage("Logout success");
        return ResponseEntity.ok(responseDTO);
    }

}
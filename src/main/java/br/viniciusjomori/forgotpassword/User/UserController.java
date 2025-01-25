package br.viniciusjomori.forgotpassword.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.viniciusjomori.forgotpassword.User.DTO.UserRegisterDTO;
import br.viniciusjomori.forgotpassword.User.DTO.UserResponseDTO;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService service;

    @Autowired
    private UserMapper mapper;

    @PostMapping
    public ResponseEntity<UserResponseDTO> registerUser(@RequestBody UserRegisterDTO register) {
        UserEntity entity = service.registerUser(register);
        UserResponseDTO response = mapper.toResponse(entity);
        return ResponseEntity.ok(response);
    }
    
}

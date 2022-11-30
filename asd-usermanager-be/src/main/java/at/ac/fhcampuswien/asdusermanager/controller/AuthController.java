package at.ac.fhcampuswien.asdusermanager.controller;

import at.ac.fhcampuswien.asdusermanager.dto.LoginDTO;
import at.ac.fhcampuswien.asdusermanager.dto.LoginResponseDTO;
import at.ac.fhcampuswien.asdusermanager.dto.RegisterDTO;
import at.ac.fhcampuswien.asdusermanager.service.AuthService;
import at.ac.fhcampuswien.asdusermanager.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class AuthController {

    UserService userService;
    AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<Void> registration(@Valid @RequestBody RegisterDTO registerDTO) {
        userService.registerUser(registerDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginDTO loginDTO) {
        return ResponseEntity.ok(authService.login(loginDTO));

    }
}

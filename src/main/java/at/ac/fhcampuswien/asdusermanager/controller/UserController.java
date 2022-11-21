package at.ac.fhcampuswien.asdusermanager.controller;

import at.ac.fhcampuswien.asdusermanager.dto.UserDTO;
import at.ac.fhcampuswien.asdusermanager.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/my")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class UserController {

    UserService userService;

    @GetMapping("/user")
    public ResponseEntity<UserDTO> getMyData() {
        return ResponseEntity.ok(userService.getMyUser());
    }
}

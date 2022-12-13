package at.ac.fhcampuswien.asdusermanager.service;

import at.ac.fhcampuswien.asdusermanager.dto.AuthenticationDTO;
import at.ac.fhcampuswien.asdusermanager.dto.LoginDTO;
import at.ac.fhcampuswien.asdusermanager.entity.RefreshTokenEntity;
import at.ac.fhcampuswien.asdusermanager.entity.UserEntity;
import at.ac.fhcampuswien.asdusermanager.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceImplTest {
    @Mock
    AuthenticationManager authenticationManager;
    @Mock
    JwtProvider jwtProvider;
    @Mock
    RefreshTokenService refreshTokenService;
    @Mock
    UserRepository userRepository;
    @InjectMocks
    UserServiceImpl userService;

    @Test
    @DisplayName("Should login user and reset failed attempts")
    void login() {
        // Arrange
        LoginDTO loginDto = createLoginDto();
        String token = "MY_AWESOME_TOKEN";
        Optional<UserEntity> userEntity = Optional.of(getUserEntity());
        Mockito.when(authenticationManager.authenticate(Mockito.any())).thenReturn(new UsernamePasswordAuthenticationToken(loginDto.userName(), loginDto.password()));
        Mockito.when(jwtProvider.generateToken(Mockito.any())).thenReturn(token);
        Mockito.when(refreshTokenService.generateRefreshToken()).thenReturn(new RefreshTokenEntity());
        Mockito.when(userRepository.findByUserName(loginDto.userName())).thenReturn(userEntity);

        // Act
        AuthenticationDTO login = userService.login(loginDto);

        // Assert
        assertEquals(token, login.authToken());
        assertEquals(0, userEntity.get().getFailedAttempts());
    }

    @Test
    @DisplayName("Should increase error attempt upon wrong password")
    void login_wrong_username() {
        // Arrange
        LoginDTO loginDto = createLoginDto();
        Optional<UserEntity> userEntity = Optional.of(getUserEntity());

        Mockito.when(authenticationManager.authenticate(Mockito.any())).thenThrow(BadCredentialsException.class);
        Mockito.when(userRepository.findByUserName(loginDto.userName())).thenReturn(userEntity);

        // Act
        AuthenticationDTO login = userService.login(loginDto);

        // Assert
        assertNull(login);
        assertEquals(101, userEntity.get().getFailedAttempts());
    }

    @Test
    @DisplayName("Should throw error when logged in attempts exceeded")
    void login_too_many_failed_attempts() {
        LoginDTO loginDto = createLoginDto();

        Mockito.when(authenticationManager.authenticate(Mockito.any())).thenThrow(LockedException.class);

        assertThrows(ResponseStatusException.class, () -> userService.login(loginDto));
    }

    private LoginDTO createLoginDto() {
        return new LoginDTO("Dwight", "auto2413");
    }

    private UserEntity getUserEntity() {
        UserEntity userEntity = new UserEntity();
        userEntity.setFailedAttempts(100);
        return userEntity;
    }
}

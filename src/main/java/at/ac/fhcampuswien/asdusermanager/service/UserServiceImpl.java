package at.ac.fhcampuswien.asdusermanager.service;

import at.ac.fhcampuswien.asdusermanager.dto.*;
import at.ac.fhcampuswien.asdusermanager.entity.UserEntity;
import at.ac.fhcampuswien.asdusermanager.mapper.UserMapper;
import at.ac.fhcampuswien.asdusermanager.mapper.crypto.PasswordEncoderMapper;
import at.ac.fhcampuswien.asdusermanager.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@Service
@Log4j2
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class UserServiceImpl implements UserService {
    UserRepository repository;
    UserMapper userMapper;
    AuthenticationManager authenticationManager;
    JwtProvider jwtProvider;
    PasswordEncoderMapper passwordEncoderMapper;
    RefreshTokenService refreshTokenService;


    @Override
    @Transactional
    public void registerUser(RegisterDTO registerDTO) {
        UserEntity userEntity = userMapper.toEntity(registerDTO);
        try {
            repository.save(userEntity);
        } catch (DataIntegrityViolationException e) {
            log.error("Username {} already exists", registerDTO.userName());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username already exists");
        }
    }

    @Override
    @Transactional
    public void changePassword(ChangePasswordDTO passwordDTO) {
        if (!passwordDTO.newPassword().equals(passwordDTO.verifiedPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password mismatch");
        }
        UserEntity user = getUserEntity(getUserName());
        validatePassword(passwordDTO.currentPassword(), user.getPassword());

        user.setPassword(passwordEncoderMapper.encodePlainText(passwordDTO.newPassword()));
    }

    @Override
    @Transactional
    public void deleteMyAccount() {
        String userName = getUserName();
        log.info("deleting account {}", userName);
        repository.deleteByUserName(userName);
    }

    @Override
    public void verifyPassword(String password) {
        validatePassword(password, getUserEntity(getUserName()).getPassword());
    }

    private void validatePassword(String password, String dbPassword) {
        if (!passwordEncoderMapper.doesPasswordMatch(password, dbPassword)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password wrong");
        }
    }


    /**
     * Authenticates a user based on username and password in spring security context
     */
    @Override
    @Transactional
    public AuthenticationDTO login(LoginDTO loginDTO) {
        try {
            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.userName(), loginDTO.password()));
            SecurityContextHolder.getContext().setAuthentication(authenticate);
            String token = jwtProvider.generateToken(authenticate);

            getUserEntity(loginDTO.userName()).resetFailedAttempts();
            return new AuthenticationDTO(token, refreshTokenService.generateRefreshToken().getToken(), LocalDateTime.now().plusSeconds(jwtProvider.getJwtExpirationInMillis()), loginDTO.userName());
        } catch (BadCredentialsException e) {
            log.info("increasing failure count for user {}", loginDTO.userName());
            getUserEntity(loginDTO.userName()).increaseFailedAttempt();
        } catch (UsernameNotFoundException e) {
            log.error("Could not find user name {}", loginDTO.userName());
        } catch (LockedException e) {
            log.error("user account is locked");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "locked until: " + getUserEntity(loginDTO.userName()).getLockedUntil());
        }
        return null;
    }

    @Override
    public AuthenticationDTO refreshAccessToken(RefreshTokenDTO refreshTokenRequest) {
        refreshTokenService.validateRefreshToken(refreshTokenRequest.refreshToken());
        return new AuthenticationDTO(jwtProvider.generateTokenWithUserName(refreshTokenRequest.userName()), refreshTokenService.generateRefreshToken().getToken(), LocalDateTime.now().plusSeconds(jwtProvider.getJwtExpirationInMillis()), refreshTokenRequest.userName());
    }

    private String getUserName() {
        Jwt principal = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return principal.getSubject();
    }

    private UserEntity getUserEntity(String userName) {
        return repository.findByUserName(userName)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User cannot be found"));
    }

}

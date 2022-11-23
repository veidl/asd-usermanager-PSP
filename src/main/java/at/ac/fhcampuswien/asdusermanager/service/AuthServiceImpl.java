package at.ac.fhcampuswien.asdusermanager.service;

import at.ac.fhcampuswien.asdusermanager.dto.LoginDTO;
import at.ac.fhcampuswien.asdusermanager.dto.LoginResponseDTO;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class AuthServiceImpl implements AuthService {

    AuthenticationManager authenticationManager;
    JwtProvider jwtProvider;

    @Override
    public LoginResponseDTO login(LoginDTO loginDTO) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.userName(), loginDTO.password()));
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        String token = jwtProvider.generateToken(authenticate);

        return new LoginResponseDTO(token, LocalDateTime.now().plusSeconds(jwtProvider.getJwtExpirationInMillis()), loginDTO.userName());
    }

    @Override
    public String getUserName() {
        Jwt principal = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return principal.getSubject();
    }


}

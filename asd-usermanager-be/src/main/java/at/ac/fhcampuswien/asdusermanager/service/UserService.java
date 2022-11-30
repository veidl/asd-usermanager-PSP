package at.ac.fhcampuswien.asdusermanager.service;

import at.ac.fhcampuswien.asdusermanager.dto.*;

public interface UserService {

    void registerUser(RegisterDTO registerDTO);

    AuthenticationDTO login(LoginDTO loginDTO);

    AuthenticationDTO refreshAccessToken(RefreshTokenDTO refreshTokenRequest);

    void changePassword(ChangePasswordDTO passwordDTO);

    void deleteMyAccount();

    void verifyPassword(String password);
}

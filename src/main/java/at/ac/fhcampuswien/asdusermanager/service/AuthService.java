package at.ac.fhcampuswien.asdusermanager.service;

import at.ac.fhcampuswien.asdusermanager.dto.LoginDTO;
import at.ac.fhcampuswien.asdusermanager.dto.LoginResponseDTO;

public interface AuthService {

    LoginResponseDTO login(LoginDTO loginDTO);

    String getUserName();
}

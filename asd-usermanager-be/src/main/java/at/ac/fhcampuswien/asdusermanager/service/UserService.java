package at.ac.fhcampuswien.asdusermanager.service;

import at.ac.fhcampuswien.asdusermanager.dto.RegisterDTO;
import at.ac.fhcampuswien.asdusermanager.dto.UserDTO;

public interface UserService {

    void registerUser(RegisterDTO registerDTO);

    UserDTO getMyUser();
}

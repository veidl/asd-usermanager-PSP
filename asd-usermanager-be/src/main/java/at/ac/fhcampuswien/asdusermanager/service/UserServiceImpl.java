package at.ac.fhcampuswien.asdusermanager.service;

import at.ac.fhcampuswien.asdusermanager.dto.RegisterDTO;
import at.ac.fhcampuswien.asdusermanager.dto.UserDTO;
import at.ac.fhcampuswien.asdusermanager.entity.UserEntity;
import at.ac.fhcampuswien.asdusermanager.mapper.UserMapper;
import at.ac.fhcampuswien.asdusermanager.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@Log4j2
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class UserServiceImpl implements UserService {

    AuthService authService;
    UserRepository repository;
    UserMapper userMapper;

    @Override
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
    @Transactional(readOnly = true)
    public UserDTO getMyUser() {
        return userMapper.toDto(repository.findByUserName(authService.getUserName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found")));
    }
}

package at.ac.fhcampuswien.asdusermanager.controller;

import at.ac.fhcampuswien.asdusermanager.dto.ChangePasswordDTO;
import at.ac.fhcampuswien.asdusermanager.dto.RegisterDTO;
import at.ac.fhcampuswien.asdusermanager.entity.UserEntity;
import at.ac.fhcampuswien.asdusermanager.mapper.UserMapper;
import at.ac.fhcampuswien.asdusermanager.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    UserMapper userMapper;


    @BeforeAll
    public static void setUp() {

    }

    @Test
    void should_create_user() throws Exception {
        RegisterDTO registerDTO = new RegisterDTO("dwight", "test", "test", "auto2413");

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/auth/signup")
                        .accept(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(registerDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        UserEntity dwight = userRepository.findByUserName("dwight").get();

        Assertions.assertNotNull(dwight);
        Assertions.assertEquals(dwight.getUserName(), "dwight");

    }

    @Test
    void user_already_exists_at_registration() throws Exception {
        RegisterDTO registerDTO = new RegisterDTO("dwight2", "test", "test", "auto2413");

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/auth/signup")
                        .accept(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(registerDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        String error = mockMvc.perform(MockMvcRequestBuilders
                        .post("/auth/signup")
                        .accept(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(registerDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn().getResolvedException().getMessage();

        UserEntity dwight = userRepository.findByUserName("dwight2").get();

        Assertions.assertNotNull(dwight);
        Assertions.assertEquals("400 BAD_REQUEST \"Username already exists\"", error);

    }

    @Test
    void unauthenticated_access() throws Exception {
        ChangePasswordDTO changePasswordDTO = new ChangePasswordDTO("auto2413", "auto1234", "auto1234");

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/password")
                        .accept(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(changePasswordDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());

    }

    @Test
    @WithMockUser(username = "dwight")
    void password_change_mismatch() throws Exception {
        // Arrange
        ChangePasswordDTO changePasswordDTO = new ChangePasswordDTO("auto2413", "auto1234", "auto12345");
        this.userRepository.save(getUserEntity());


        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/password")
                        .accept(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(changePasswordDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn().getResolvedException().getMessage();
    }

    private UserEntity getUserEntity() {
        RegisterDTO dto = new RegisterDTO("dwighto", "Dwightoo", "Schruteu", "auto2413");
        return userMapper.toEntity(dto);
    }

}

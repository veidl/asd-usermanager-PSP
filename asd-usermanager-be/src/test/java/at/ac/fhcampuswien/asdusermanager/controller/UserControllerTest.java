package at.ac.fhcampuswien.asdusermanager.controller;

import at.ac.fhcampuswien.asdusermanager.dto.RegisterDTO;
import at.ac.fhcampuswien.asdusermanager.entity.UserEntity;
import at.ac.fhcampuswien.asdusermanager.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
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


}

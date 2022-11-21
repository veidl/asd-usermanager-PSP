package at.ac.fhcampuswien.asdusermanager.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public record RegisterDTO(
        @NotEmpty
        @Size(min = 3, max = 32, message = "Username must be between {min} and {max} characters")
        String userName,

        @NotEmpty
        @Size(min = 3, max = 30, message = "First name must be between {min} and {max} characters")
        String firstName,

        @NotEmpty
        @Size(min = 3, max = 30, message = "Last name must be between {min} and {max} characters")
        String lastName,

        @NotEmpty
        @Size(min = 8, max = 30, message = "Password must be between {min} and {max} characters")
        String password) {
}


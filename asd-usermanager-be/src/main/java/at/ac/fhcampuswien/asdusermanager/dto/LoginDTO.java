package at.ac.fhcampuswien.asdusermanager.dto;

import javax.validation.constraints.NotEmpty;

public record LoginDTO(
        @NotEmpty
        String userName,
        @NotEmpty
        String password
) {
}

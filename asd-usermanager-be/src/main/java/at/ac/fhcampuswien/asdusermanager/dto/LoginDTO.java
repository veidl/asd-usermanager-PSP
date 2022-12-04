package at.ac.fhcampuswien.asdusermanager.dto;

import javax.validation.constraints.NotEmpty;

public record LoginDTO(
        @NotEmpty(message = "{inputNotEmpty}")
        String userName,
        @NotEmpty(message = "{inputNotEmpty}")
        String password
) {
}

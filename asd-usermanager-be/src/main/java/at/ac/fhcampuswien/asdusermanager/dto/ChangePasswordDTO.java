package at.ac.fhcampuswien.asdusermanager.dto;

import javax.validation.constraints.NotEmpty;

public record ChangePasswordDTO(
        @NotEmpty(message = "{inputNotEmpty}")
        String currentPassword,
        @NotEmpty(message = "{inputNotEmpty}")
        String newPassword,
        @NotEmpty(message = "{inputNotEmpty}")
        String verifiedPassword
) {
}

package at.ac.fhcampuswien.asdusermanager.dto;

import javax.validation.constraints.NotEmpty;

public record ChangePasswordDTO(
        @NotEmpty
        String currentPassword,
        @NotEmpty
        String newPassword,
        @NotEmpty
        String verifiedPassword
) {
}

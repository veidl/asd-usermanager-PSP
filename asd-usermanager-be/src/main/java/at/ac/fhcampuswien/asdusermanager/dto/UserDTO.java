package at.ac.fhcampuswien.asdusermanager.dto;

public record UserDTO(
        String firstName,
        String lastName,
        String userName,
        String password
) {
}

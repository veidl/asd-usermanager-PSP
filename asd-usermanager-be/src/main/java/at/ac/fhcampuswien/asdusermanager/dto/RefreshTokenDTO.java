package at.ac.fhcampuswien.asdusermanager.dto;

public record RefreshTokenDTO(
        String refreshToken,
        String userName
) {
}

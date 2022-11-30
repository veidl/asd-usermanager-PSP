package at.ac.fhcampuswien.asdusermanager.dto;

import java.time.LocalDateTime;


public record AuthenticationDTO(String authToken,
                                String refreshToken,
                                LocalDateTime expiresAt,
                                String userName) {
}

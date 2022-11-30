package at.ac.fhcampuswien.asdusermanager.dto;

import java.time.LocalDateTime;


public record LoginResponseDTO(String authToken,
                               LocalDateTime expiresAt,
                               String userName) {
}

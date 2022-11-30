package at.ac.fhcampuswien.asdusermanager.service;

import at.ac.fhcampuswien.asdusermanager.entity.RefreshTokenEntity;

public interface RefreshTokenService {
    RefreshTokenEntity generateRefreshToken();

    void validateRefreshToken(String token);

    void deleteRefreshToken(String token);
}

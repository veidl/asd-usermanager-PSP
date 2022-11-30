package at.ac.fhcampuswien.asdusermanager.repository;

import at.ac.fhcampuswien.asdusermanager.entity.RefreshTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, Long> {
    Optional<RefreshTokenEntity> findByToken(String token);

    void deleteByToken(String token);
}

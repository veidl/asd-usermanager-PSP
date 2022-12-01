package at.ac.fhcampuswien.asdusermanager.repository;

import at.ac.fhcampuswien.asdusermanager.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByUserName(String userName);

    void deleteByUserName(String userName);
}

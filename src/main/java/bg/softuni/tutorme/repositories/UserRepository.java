package bg.softuni.tutorme.repositories;

import bg.softuni.tutorme.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsername(String username);

    boolean existsByUsernameOrEmail(String username, String email);

    List<UserEntity> findAllByIsFeaturedTrue();
}

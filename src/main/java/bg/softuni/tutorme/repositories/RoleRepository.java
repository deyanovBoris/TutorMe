package bg.softuni.tutorme.repositories;

import bg.softuni.tutorme.entities.UserRoleEntity;
import bg.softuni.tutorme.entities.enums.UserRoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<UserRoleEntity, Long> {
    Optional<UserRoleEntity> findByRole(UserRoleEnum roleEnum);
    boolean existsByRole(UserRoleEnum userRoleEnum);
}

package bg.softuni.tutorme.repositories;

import bg.softuni.tutorme.entities.TutorApplication;
import bg.softuni.tutorme.entities.enums.ApplicationStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TutorApplicationRepository extends JpaRepository<TutorApplication, Long> {
    boolean existsByApplicantUsernameAndIsCompletedFalse(String username);

    boolean existsByApplicantUsernameAndApplicationStatus(String username, ApplicationStatusEnum applicationStatusEnum);

    List<TutorApplication> findAllByIsCompletedTrueAndApplicationStatus(ApplicationStatusEnum applicationStatusEnum);
}

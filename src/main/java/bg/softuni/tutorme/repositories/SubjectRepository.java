package bg.softuni.tutorme.repositories;

import bg.softuni.tutorme.entities.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {
    List<Subject> findAllByName(String name);

    List<Subject> findByIsFeaturedTrue();

}

package bg.softuni.tutorme.repositories;

import bg.softuni.tutorme.entities.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    List<Course> findAllBySubjectsId(long subjectsId);

    Optional<Course> findByTitle(String title);
}

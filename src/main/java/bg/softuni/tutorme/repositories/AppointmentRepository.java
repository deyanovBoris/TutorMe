package bg.softuni.tutorme.repositories;

import bg.softuni.tutorme.entities.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    boolean existsByCourseIdAndDate(long courseId, LocalDateTime date);
}

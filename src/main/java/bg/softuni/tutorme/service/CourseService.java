package bg.softuni.tutorme.service;

import bg.softuni.tutorme.entities.Course;
import bg.softuni.tutorme.entities.dtos.courses.CourseAddDTO;
import bg.softuni.tutorme.entities.dtos.courses.CourseInfoDTO;
import bg.softuni.tutorme.entities.dtos.courses.CourseShortInfoDTO;
import bg.softuni.tutorme.service.exceptions.UserNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CourseService {
    List<CourseShortInfoDTO> getAllCoursesBySubjectId(long subjectId);

    boolean submitCourse(CourseAddDTO courseAddDTO) throws UserNotFoundException;

    List<CourseShortInfoDTO> getAllCourses();

    Page<CourseShortInfoDTO> findPaginated(int pageNo, int pageSize);

    CourseInfoDTO getCourseById(long id);
}

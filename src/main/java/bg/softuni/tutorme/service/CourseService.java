package bg.softuni.tutorme.service;

import bg.softuni.tutorme.entities.dtos.courses.CourseAddDTO;
import bg.softuni.tutorme.entities.dtos.courses.CourseInfoDTO;
import bg.softuni.tutorme.entities.dtos.courses.CourseShortInfoDTO;
import bg.softuni.tutorme.service.exceptions.CourseNotFoundException;
import bg.softuni.tutorme.service.exceptions.UserNotFoundException;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CourseService {
    List<CourseShortInfoDTO> getAllCoursesBySubjectId(long subjectId);

    boolean submitCourse(CourseAddDTO courseAddDTO) throws UserNotFoundException;

    List<CourseShortInfoDTO> getAllCourses();

    Page<CourseShortInfoDTO> findPaginated(int pageNo, int pageSize);

    CourseInfoDTO getCourseById(long id) throws CourseNotFoundException;

    boolean enrollInCourse(String username, long courseId) throws UserNotFoundException;

    boolean isCourseOwner(String username, long courseId);
}

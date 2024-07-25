package bg.softuni.tutorme.service.impl;

import bg.softuni.tutorme.entities.Course;
import bg.softuni.tutorme.entities.UserEntity;
import bg.softuni.tutorme.entities.dtos.AppointmentCourseDTO;
import bg.softuni.tutorme.entities.dtos.AppointmentDTO;
import bg.softuni.tutorme.entities.dtos.InstructorDTO;
import bg.softuni.tutorme.entities.dtos.StudentsShortInfoDto;
import bg.softuni.tutorme.entities.dtos.courses.CourseAddDTO;
import bg.softuni.tutorme.entities.dtos.courses.CourseInfoDTO;
import bg.softuni.tutorme.entities.dtos.courses.CourseShortInfoDTO;
import bg.softuni.tutorme.repositories.CourseRepository;
import bg.softuni.tutorme.repositories.SubjectRepository;
import bg.softuni.tutorme.repositories.UserRepository;
import bg.softuni.tutorme.service.CourseService;
import bg.softuni.tutorme.service.exceptions.UserNotFoundException;
import org.hibernate.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class CourseServiceImpl implements CourseService {
    private final ModelMapper modelMapper;
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final SubjectRepository subjectRepository;

    public CourseServiceImpl(ModelMapper modelMapper, CourseRepository courseRepository, UserRepository userRepository, SubjectRepository subjectRepository) {
        this.modelMapper = modelMapper;
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
        this.subjectRepository = subjectRepository;
    }

    @Override
    public List<CourseShortInfoDTO> getAllCoursesBySubjectId(long subjectId) {
        return this.courseRepository.findAllBySubjectsId(subjectId)
                .stream()
                .map(c -> this.modelMapper.map(c, CourseShortInfoDTO.class)
                        .setInstructorName(c.getCourseOwner().getName())
                        .setDurationInWeeks(ChronoUnit.DAYS.between(c.getStartDate(), c.getEndDate())))
                .collect(Collectors.toList());
    }

    @Override
    public boolean submitCourse(CourseAddDTO courseAddDTO) throws UserNotFoundException {
        Optional<UserEntity> userByUsername = this.userRepository.findByUsername(courseAddDTO.getOwnerUsername());

        if (!userByUsername.isPresent()){
            throw new UserNotFoundException();
        }

        UserEntity user = userByUsername.get();

        Course courseMap = this.modelMapper.map(courseAddDTO, Course.class);
        courseMap.setSubjects(this.subjectRepository.findAllById(courseAddDTO.getSubjects()));
        courseMap.setCourseOwner(user);

        this.courseRepository.save(courseMap);

        return true;
    }

    @Override
    public List<CourseShortInfoDTO> getAllCourses() {
        return this.courseRepository.findAll()
                .stream()
                .map(c -> this.modelMapper.map(c, CourseShortInfoDTO.class)
                        .setInstructorName(c.getCourseOwner().getName())
                        .setDurationInWeeks(ChronoUnit.DAYS.between(c.getStartDate(), c.getEndDate())))
                .collect(Collectors.toList());
    }

    @Override
    public Page<CourseShortInfoDTO> findPaginated(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        Page<Course> coursePage = this.courseRepository.findAll(pageable);

        List<CourseShortInfoDTO> dtoList = coursePage
                .stream()
                .map(c -> this.modelMapper.map(c, CourseShortInfoDTO.class)
                        .setInstructorName(c.getCourseOwner().getName())
                        .setDurationInWeeks(ChronoUnit.DAYS.between(c.getStartDate(), c.getEndDate())))
                .collect(Collectors.toList());

        return new PageImpl<>(dtoList, PageRequest.of(coursePage.getNumber(), coursePage.getSize()), coursePage.getTotalElements());
    }

    @Override
    @Transactional
    public CourseInfoDTO getCourseById(long id) {
        Course course = this.courseRepository
                .findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(id, "course"));

        CourseInfoDTO courseInfoDTO = this.modelMapper.map(course, CourseInfoDTO.class);

        courseInfoDTO.setStudents(getStudents(course));
        courseInfoDTO.setInstructor(getInstructor(course));
        courseInfoDTO.setAppointments(getAppointments(course));

        return courseInfoDTO;
    }

    private static List<AppointmentCourseDTO> getAppointments(Course course) {
        return course.getAppointments()
                .stream()
                .map(a -> new AppointmentCourseDTO()
                        .setUsername(a.getMadeByUser().getUsername())
                        .setId(a.getId())
                        .setStudentName(a.getMadeByUser().getName())
                        .setDate(a.getDate()))
                .collect(Collectors.toList());
    }

    private static List<StudentsShortInfoDto> getStudents(Course course) {
        return course.getStudents()
                .stream()
                .map(s -> new StudentsShortInfoDto()
                        .setName(s.getName())
                        .setPhotoUrl(s.getProfilePhotoUrl()))
                .limit(5)
                .collect(Collectors.toList());
    }
    private static InstructorDTO getInstructor(Course course) {
        return new InstructorDTO()
                .setName(course.getCourseOwner().getName())
                .setPhotoUrl(course.getCourseOwner().getProfilePhotoUrl())
                .setBiography(course.getCourseOwner().getBiography());
    }

    @Override
    @Transactional
    public boolean enrollInCourse(String username, long courseId) throws UserNotFoundException {
        UserEntity user = this.userRepository.findByUsername(username).orElseThrow(UserNotFoundException::new);

        Course course = this.courseRepository.findById(courseId)
                .orElseThrow(() -> new ObjectNotFoundException("Course", courseId));//todo FixException to not be hibernate

        course.getStudents().add(user);
        user.getCoursesAttending().add(course);

        this.courseRepository.save(course);
        this.userRepository.save(user);

        return true;
    }

    @Override
    public boolean isCourseOwner(String username, long courseId) {
        return this.courseRepository.findById(courseId)
                .orElseThrow(() -> new ObjectNotFoundException("Course", courseId))//todo FixException to not be hibernate
                .getCourseOwner()
                .getUsername()
                .equals(username);
    }
}

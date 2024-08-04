package bg.softuni.tutorme.service.impl;

import bg.softuni.tutorme.entities.Course;
import bg.softuni.tutorme.entities.Subject;
import bg.softuni.tutorme.entities.UserEntity;
import bg.softuni.tutorme.entities.dtos.courses.CourseAddDTO;
import bg.softuni.tutorme.entities.dtos.courses.CourseShortInfoDTO;
import bg.softuni.tutorme.entities.enums.CourseType;
import bg.softuni.tutorme.repositories.CourseRepository;
import bg.softuni.tutorme.repositories.SubjectRepository;
import bg.softuni.tutorme.repositories.UserRepository;
import bg.softuni.tutorme.service.exceptions.UserNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
public class CourseServiceImplIT {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private SubjectRepository subjectRepository;
    @Autowired
    private CourseServiceImpl courseService;


    @BeforeEach
    public void beforeEach() {
        this.userRepository.save(
                new UserEntity()
                    .setEmail("test@email.com")
                        .setUsername("test")
                        .setPassword("testPass")
                        .setName("testName")
                        .setFeatured(false)
        );

        this.subjectRepository.saveAll(
                List.of(
                        new Subject()
                                .setName("testSubj")
                                .setFeatured(false)
                                .setPictureUrl("https://test.com"),
                        new Subject()
                                .setName("testSubj2")
                                .setFeatured(false)
                                .setPictureUrl("https://test2.com")
                )
        );
    }

    @AfterEach
    public void afterEach() {
        this.courseRepository.deleteAll();
        this.subjectRepository.deleteAll();
        this.userRepository.deleteAll();
    }

    @Test
    @Transactional
    public void testSubmitCourse() throws UserNotFoundException {
        CourseAddDTO courseAddDTO = new CourseAddDTO()
                .setOwnerUsername("test")
                .setCourseType(CourseType.INDIVIDUAL)
                .setSubjects(List.of(1L, 2L))
                .setCourseImageUrl("https://example.com")
                .setDescription("test desc")
                .setStartDate(LocalDate.of(2024, 10, 10))
                .setEndDate(LocalDate.of(2024, 10, 11))
                .setTitle("testCourse");

        courseService.submitCourse(courseAddDTO);

        //get course that we just submitted
        Course byId = courseRepository.findByTitle(courseAddDTO.getTitle()).get();

        Assertions.assertEquals(courseAddDTO.getTitle(), byId.getTitle());
        Assertions.assertEquals(courseAddDTO.getOwnerUsername(), byId.getCourseOwner().getUsername());
        Assertions.assertEquals(courseAddDTO.getCourseType().name(), byId.getCourseType().name());
        Assertions.assertEquals(courseAddDTO.getEndDate(), byId.getEndDate());
        Assertions.assertEquals(courseAddDTO.getDescription(), byId.getDescription());
        Assertions.assertEquals(2, byId.getSubjects().size());
    }

    @Test
    @Transactional
    void testGetAllCoursesReturnsProperly(){
        Course course1 = new Course()
                .setCourseOwner(this.userRepository.findByUsername("test").get())
                .setCourseType(CourseType.INDIVIDUAL)
                .setSubjects(List.of(this.subjectRepository.findById(1L).get()))
                .setCourseImageUrl("https://example.com")
                .setDescription("test desc")
                .setStartDate(LocalDate.of(2024, 10, 10))
                .setEndDate(LocalDate.of(2024, 10, 11))
                .setTitle("testCourse");

        Course course2 = new Course()
                .setCourseOwner(this.userRepository.findByUsername("test").get())
                .setCourseType(CourseType.INDIVIDUAL)
                .setSubjects(List.of(this.subjectRepository.findById(2L).get()))
                .setCourseImageUrl("https://example.com/test")
                .setDescription("test desc")
                .setStartDate(LocalDate.of(2024, 11, 10))
                .setEndDate(LocalDate.of(2024, 12, 11))
                .setTitle("testCourse2");


        this.courseRepository.saveAll(List.of(course1, course2));

        List<CourseShortInfoDTO> allCourses = this.courseService.getAllCourses();

        Assertions.assertInstanceOf(java.util.List.class, allCourses);
        Assertions.assertInstanceOf(CourseShortInfoDTO.class, allCourses.getFirst());
        Assertions.assertEquals(2, allCourses.size());
        Assertions.assertEquals(1, allCourses.getFirst().getDurationInWeeks());
    }



}

package bg.softuni.tutorme.service.impl;

import bg.softuni.tutorme.entities.Course;
import bg.softuni.tutorme.entities.UserEntity;
import bg.softuni.tutorme.entities.dtos.courses.CourseAddDTO;
import bg.softuni.tutorme.entities.enums.CourseType;
import bg.softuni.tutorme.repositories.CourseRepository;
import bg.softuni.tutorme.repositories.SubjectRepository;
import bg.softuni.tutorme.repositories.UserRepository;
import bg.softuni.tutorme.service.exceptions.UserNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class CourseServiceImplTest {

    private CourseServiceImpl toTest;
    @Mock
    private ModelMapper mockModelMapper;
    @Mock
    private CourseRepository mockCourseRepository;
    @Mock
    private UserRepository mockUserRepository;
    @Mock
    private SubjectRepository mockSubjectRepository;
    @Captor
    private ArgumentCaptor<Course> courseCaptor;


    @BeforeEach
    void setUp(){
        toTest = new CourseServiceImpl(
                mockModelMapper,
                mockCourseRepository,
                mockUserRepository,
                mockSubjectRepository);
    }

    @Test
    void testSubmitCourseNoUsernameFoundShouldThrow(){
        Mockito.when(mockUserRepository.findByUsername("NonExistent"))
                .thenReturn(Optional.empty());
        CourseAddDTO courseAddDTO = new CourseAddDTO().setOwnerUsername("NonExistent");

        Assertions.assertThrows(UserNotFoundException.class, () -> toTest.submitCourse(courseAddDTO));
    }

    @Disabled
    @Test
    void testSubmitCourse(){
        CourseAddDTO courseAddDTO = new CourseAddDTO()
                .setOwnerUsername("testUser")
                .setCourseType(CourseType.INDIVIDUAL)
                .setSubjects(List.of(1L, 2L, 3L))
                .setCourseImageUrl("https://example.com")
                .setDescription("test desc")
                .setStartDate(LocalDate.of(2024, 10, 10))
                .setEndDate(LocalDate.of(2024, 10, 11))
                .setTitle("testCourse");

        UserEntity user = new UserEntity().setUsername("testUser");

        Mockito.when(mockUserRepository.findByUsername("testUser"))
                .thenReturn(Optional.of(user));
        Mockito.when(mockModelMapper.map(courseAddDTO, Course.class))
                .thenReturn(new Course());
    }

}

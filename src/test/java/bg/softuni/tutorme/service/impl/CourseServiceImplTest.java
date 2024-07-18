package bg.softuni.tutorme.service.impl;

import bg.softuni.tutorme.repositories.CourseRepository;
import bg.softuni.tutorme.repositories.SubjectRepository;
import bg.softuni.tutorme.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

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


    @BeforeEach
    void setUp(){
        toTest = new CourseServiceImpl(
                mockModelMapper,
                mockCourseRepository,
                mockUserRepository,
                mockSubjectRepository);
    }




}

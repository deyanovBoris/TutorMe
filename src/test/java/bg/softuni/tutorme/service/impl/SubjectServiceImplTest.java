package bg.softuni.tutorme.service.impl;


import bg.softuni.tutorme.entities.Subject;
import bg.softuni.tutorme.entities.dtos.subjects.SubjectFeatureDTO;
import bg.softuni.tutorme.repositories.SubjectRepository;
import bg.softuni.tutorme.service.SubjectService;
import org.hibernate.ObjectNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SubjectServiceImplTest {
    private SubjectServiceImpl toTest;
    @Mock
    private SubjectRepository mockSubjectRepository;
    @Mock
    private ModelMapper mockModelMapper;
    @Mock
    private Random mockRandom;

    private Subject subject1;
    private Subject subject2;
    private Subject subject3;
    private Subject subject4;

    private SubjectFeatureDTO subjectFeatureDTO1;
    private SubjectFeatureDTO subjectFeatureDTO2;
    private SubjectFeatureDTO subjectFeatureDTO3;

    @BeforeEach
    void setUp(){
        toTest = new SubjectServiceImpl(
                mockSubjectRepository,
                mockModelMapper,
                mockRandom);

        subject1 = new Subject();
        subject2 = new Subject();
        subject3 = new Subject();
        subject4 = new Subject();

        subjectFeatureDTO1 = new SubjectFeatureDTO();
        subjectFeatureDTO2 = new SubjectFeatureDTO();
        subjectFeatureDTO3 = new SubjectFeatureDTO();
    }

    @Test
    public void testGetFeaturedSubjects() {
        List<Subject> featuredSubjects = Arrays.asList(subject1, subject2, subject3, subject4);
        when(mockSubjectRepository.findByIsFeaturedTrue())
                .thenReturn(featuredSubjects);

        when(mockModelMapper.map(subject1, SubjectFeatureDTO.class)).thenReturn(subjectFeatureDTO1);
        when(mockModelMapper.map(subject2, SubjectFeatureDTO.class)).thenReturn(subjectFeatureDTO2);
        when(mockModelMapper.map(subject3, SubjectFeatureDTO.class)).thenReturn(subjectFeatureDTO3);

        List<SubjectFeatureDTO> result = toTest.getFeaturedSubjects();



        Assertions.assertEquals(3, result.size());
        List<SubjectFeatureDTO> expectedResults = Arrays.asList(
                        subjectFeatureDTO1,
                        subjectFeatureDTO2,
                        subjectFeatureDTO3);

        Assertions.assertTrue(result.containsAll(expectedResults));
    }

    @Test
    public void testGetSubjectsReturnsProperlyAndIsOrdered() {

        subject1 = new Subject().setName("Bulgarian");
        subject2 = new Subject().setName("Maths");
        subject3 = new Subject().setName("Spanish");

        List<Subject> allSubjects = Arrays.asList(subject2, subject1, subject3);
        when(mockSubjectRepository.findAll()).thenReturn(allSubjects);

        when(mockModelMapper.map(subject1, SubjectFeatureDTO.class)).thenReturn(subjectFeatureDTO1);
        when(mockModelMapper.map(subject2, SubjectFeatureDTO.class)).thenReturn(subjectFeatureDTO2);
        when(mockModelMapper.map(subject3, SubjectFeatureDTO.class)).thenReturn(subjectFeatureDTO3);

        List<SubjectFeatureDTO> result = toTest.getSubjects();

        Assertions.assertEquals(3, result.size());
        Assertions.assertEquals(subjectFeatureDTO1, result.get(0));
        Assertions.assertEquals(subjectFeatureDTO2, result.get(1));
        Assertions.assertEquals(subjectFeatureDTO3, result.get(2));
    }

    @Test
    public void testGetSubjectNameByIdThrowsWhenNonExistingSubject() {
        long subjectId = 1L;
        when(mockSubjectRepository.findById(subjectId)).thenReturn(Optional.empty());

        Assertions.assertThrows(ObjectNotFoundException.class, () -> {
            toTest.getSubjectNameById(subjectId);
        });
    }

    @Test
    public void testGetSubjectNameById_ReturnsName_WhenSubjectFound() {
        Subject subject = new Subject()
                .setId(1L)
                .setName("TestName");
        long subjectId = 1L;
        when(mockSubjectRepository.findById(subjectId)).thenReturn(Optional.of(subject));

        String result = toTest.getSubjectNameById(subjectId);

        Assertions.assertEquals(subject.getName(), result);
    }

}

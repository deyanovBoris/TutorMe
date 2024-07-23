package bg.softuni.tutorme.init;

import bg.softuni.tutorme.entities.Subject;
import bg.softuni.tutorme.entities.UserRoleEntity;
import bg.softuni.tutorme.repositories.SubjectRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@ExtendWith(MockitoExtension.class)
public class InitSubjectsTest {

    private static final List<String> SUBJECT_NAMES_SORTED =
        Stream.of(
            "Mathematics", "Physics", "Chemistry",
            "Biology", "Computer Science", "History", "Geography",
            "Literature", "Art", "Physical Education")
                    .sorted()
                    .collect(Collectors.toList());
    private InitSubjects toTest;
    @Mock
    private SubjectRepository mockSubjectRepository;
    @Captor
    private ArgumentCaptor<List<Subject>> subjectCaptor;
    @BeforeEach
    void setUp(){
        toTest = new InitSubjects(mockSubjectRepository);
    }

    @Test
    void testEmptyDbInsertsSubjects(){
        Mockito.when(mockSubjectRepository.count()).thenReturn(0L);

        toTest.initializeSubjects();

        Mockito.verify(mockSubjectRepository).saveAll(subjectCaptor.capture());

        List<String> subjectNames = subjectCaptor.getValue()
                .stream()
                .map(Subject::getName)
                .sorted()
                .collect(Collectors.toList());

        Assertions.assertLinesMatch(SUBJECT_NAMES_SORTED, subjectNames);
    }
}

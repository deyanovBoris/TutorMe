package bg.softuni.tutorme.service;

import static org.mockito.Mockito.*;

import java.util.List;

import bg.softuni.tutorme.entities.enums.ApplicationStatusEnum;
import bg.softuni.tutorme.repositories.TutorApplicationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import bg.softuni.tutorme.entities.TutorApplication;

@ExtendWith(MockitoExtension.class)
public class ScheduledTasksTest {
    @Mock
    private TutorApplicationRepository mockTutorApplicationRepository;
    private ScheduledTasks toTest;
    private static final Logger logger = LoggerFactory.getLogger(ScheduledTasks.class);

    @BeforeEach
    public void setUp() {
        toTest=new ScheduledTasks(mockTutorApplicationRepository);
    }

    @Test
    public void testClearUnapprovedTutorRequests() {
        // Arrange
        TutorApplication application1 = new TutorApplication();
        application1.setId(1L);
        TutorApplication application2 = new TutorApplication();
        application2.setId(2L);
        List<TutorApplication> rejectedApplications = List.of(application1, application2);

        when(mockTutorApplicationRepository.findAllByIsCompletedTrueAndApplicationStatus(ApplicationStatusEnum.REJECTED))
                .thenReturn(rejectedApplications);

        // Act
        toTest.clearUnapprovedTutorRequests();

        // Assert
        // Verify that findAllByIsCompletedTrueAndApplicationStatus was called
        verify(mockTutorApplicationRepository).findAllByIsCompletedTrueAndApplicationStatus(ApplicationStatusEnum.REJECTED);

        // Verify that deleteAll was called with the correct arguments
        verify(mockTutorApplicationRepository).deleteAll(rejectedApplications);

        // Verify logging (optional)
        // Use a library like `LogCaptor` to capture and assert logging output
    }
}

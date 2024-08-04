package bg.softuni.tutorme.service.impl;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentCaptor.forClass;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import bg.softuni.tutorme.entities.Subject;
import bg.softuni.tutorme.entities.TutorApplication;
import bg.softuni.tutorme.entities.UserEntity;
import bg.softuni.tutorme.entities.UserRoleEntity;
import bg.softuni.tutorme.entities.dtos.tutor.TutorApplicationDTO;
import bg.softuni.tutorme.entities.enums.ApplicationStatusEnum;
import bg.softuni.tutorme.entities.enums.UserRoleEnum;
import bg.softuni.tutorme.repositories.RoleRepository;
import bg.softuni.tutorme.repositories.SubjectRepository;
import bg.softuni.tutorme.repositories.TutorApplicationRepository;
import bg.softuni.tutorme.repositories.UserRepository;
import bg.softuni.tutorme.service.exceptions.ApplicationNotFoundException;
import bg.softuni.tutorme.service.exceptions.UserNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TutorApplicationServiceImplTest {
    @Mock
    private UserRepository mockUserRepository;
    @Mock
    private SubjectRepository mockSubjectRepository;
    @Mock
    private TutorApplicationRepository mockTutorApplicationRepository;
    @Mock
    private RoleRepository mockRoleRepository;
    private TutorApplicationServiceImpl toTest;

    private TutorApplication tutorApplication;
    private TutorApplicationDTO tutorApplicationDTO;
    private UserEntity userEntity;
    private UserRoleEntity userRoleEntity;
    private Subject subject1;
    private Subject subject2;
    @BeforeEach
    public void setUp() {
        toTest = new TutorApplicationServiceImpl(
                mockTutorApplicationRepository,
                mockUserRepository,
                mockSubjectRepository,
                mockRoleRepository);

        userEntity = new UserEntity()
                .setUsername("testuser");

        userRoleEntity = new UserRoleEntity()
                .setRole(UserRoleEnum.TUTOR);

        tutorApplication = new TutorApplication();
        tutorApplication.setId(1L);
        tutorApplication.setCompleted(false);
        tutorApplication.setApplicationStatus(ApplicationStatusEnum.PENDING);
        tutorApplication.setApplicant(userEntity);

        tutorApplicationDTO = new TutorApplicationDTO();
        tutorApplicationDTO.setApplicantUsername("testuser");
        tutorApplicationDTO.setExperience("yes");
        tutorApplicationDTO.setMotivation("I love teaching");
        tutorApplicationDTO.setPhone("1234567890");
        tutorApplicationDTO.setSubjects(List.of(1L, 2L));

        subject1 = new Subject()
                .setId(1L)
                .setName("Math");

        subject2 = new Subject()
                .setId(2L)
                .setName("Science");
    }

    @Test
    public void testSubmitTutorApplication() {
        // Arrange
        when(mockUserRepository.findByUsername("testuser"))
                .thenReturn(Optional.of(userEntity));
        when(mockSubjectRepository.findAllById(List.of(1L, 2L)))
                .thenReturn(List.of(subject1, subject2));

        // Act
        boolean result = toTest.submitTutorApplication(tutorApplicationDTO);

        // Assert
        assertTrue(result);

        ArgumentCaptor<TutorApplication> applicationCaptor = forClass(TutorApplication.class);
        verify(mockTutorApplicationRepository).save(applicationCaptor.capture());

        TutorApplication capturedApplication = applicationCaptor.getValue();

        // Verify the captured application
        Assertions.assertEquals(userEntity, capturedApplication.getApplicant());
        Assertions.assertTrue(capturedApplication.isExperienced());
        Assertions.assertEquals("I love teaching", capturedApplication.getMotivation());
        Assertions.assertEquals("1234567890", capturedApplication.getPhone());
        Assertions.assertEquals(List.of(subject1, subject2), capturedApplication.getSubjects());
        Assertions.assertFalse(capturedApplication.isCompleted());
        Assertions.assertEquals(ApplicationStatusEnum.PENDING, capturedApplication.getApplicationStatus());
    }

    @Test
    public void testApproveApplicationApplicationNotFound() {
        long applicationId = 1L;
        when(mockTutorApplicationRepository.findById(applicationId)).thenReturn(Optional.empty());

        Assertions.assertThrows(ApplicationNotFoundException.class, () -> {
            toTest.approveApplication(applicationId);
        });
    }

    @Test
    public void testApproveApplicationUserNotFound() {
        long applicationId = 1L;
        when(mockTutorApplicationRepository.findById(applicationId)).thenReturn(Optional.of(tutorApplication));
        when(mockUserRepository.findById(anyLong())).thenReturn(Optional.empty());

        Assertions.assertThrows(UserNotFoundException.class, () -> {
            toTest.approveApplication(applicationId);
        });
    }

    @Test
    public void testApproveApplication_Success() throws UserNotFoundException, ApplicationNotFoundException {
        // Arrange
        long applicationId = 1L;
        when(mockTutorApplicationRepository.findById(applicationId)).thenReturn(Optional.of(tutorApplication));
        when(mockUserRepository.findById(userEntity.getId())).thenReturn(Optional.of(userEntity));
        when(mockRoleRepository.findByRole(UserRoleEnum.TUTOR)).thenReturn(Optional.of(userRoleEntity));

        // Act
        boolean result = toTest.approveApplication(applicationId);

        // Assert
        assertTrue(result);

        // Verify that the user and tutor application were saved
        ArgumentCaptor<UserEntity> userCaptor = ArgumentCaptor.forClass(UserEntity.class);
        ArgumentCaptor<TutorApplication> applicationCaptor = ArgumentCaptor.forClass(TutorApplication.class);

        verify(mockUserRepository).save(userCaptor.capture());
        verify(mockTutorApplicationRepository).save(applicationCaptor.capture());

        UserEntity capturedUser = userCaptor.getValue();
        TutorApplication capturedApplication = applicationCaptor.getValue();

        assertTrue(capturedUser.getRoles().contains(userRoleEntity));
        assertTrue(capturedApplication.isCompleted());
        Assertions.assertEquals(ApplicationStatusEnum.APPROVED, capturedApplication.getApplicationStatus());
    }
}

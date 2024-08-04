package bg.softuni.tutorme.service.impl;

import bg.softuni.tutorme.entities.Appointment;
import bg.softuni.tutorme.entities.Course;
import bg.softuni.tutorme.entities.UserEntity;
import bg.softuni.tutorme.entities.dtos.DateTimeDTO;
import bg.softuni.tutorme.repositories.AppointmentRepository;
import bg.softuni.tutorme.repositories.CourseRepository;
import bg.softuni.tutorme.repositories.UserRepository;
import bg.softuni.tutorme.service.exceptions.CourseNotFoundException;
import bg.softuni.tutorme.service.exceptions.UserNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AppointmentServiceImplTest {
    @Mock
    private AppointmentRepository mockAppointmentRepository;
    @Mock
    private UserRepository mockUserRepository;
    @Mock
    private CourseRepository mockCourseRepository;
    @Mock
    private ModelMapper mockModelMapper;
    private AppointmentServiceImpl toTest;

    private UserEntity userEntity;
    private Course course;
    private Principal principal;
    private LocalDateTime futureDateTime;

    @BeforeEach
    public void setUp() {
        toTest = new AppointmentServiceImpl(mockAppointmentRepository,
                mockUserRepository,
                mockCourseRepository,
                mockModelMapper);

        userEntity = new UserEntity();
        userEntity.setUsername("testuser");
        userEntity.setAppointments(new ArrayList<>());

        course = new Course();
        course.setId(1L);
        course.setTitle("Test Course");
        course.setCourseOwner(userEntity);
        course.setAppointments(new ArrayList<>());

        principal = Mockito.mock(Principal.class);
//        when(principal.getName()).thenReturn(principal.getName());

        futureDateTime = LocalDateTime.now().plusDays(1);
    }

    @Test
    public void testMakeAppointmentAppointmentAlreadyExistsReturnsFalse() throws UserNotFoundException, CourseNotFoundException {
        DateTimeDTO dateTimeDTO = new DateTimeDTO();
        dateTimeDTO.setDateTime(futureDateTime.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")));
        when(mockAppointmentRepository.existsByCourseIdAndDate(anyLong(), any(LocalDateTime.class))).thenReturn(true);

        boolean result = toTest.makeAppointment(1L, dateTimeDTO, principal);

        assertFalse(result);
    }

    @Test
    public void testMakeAppointmentDateNotInFutureReturnsFalse() throws UserNotFoundException, CourseNotFoundException {
        DateTimeDTO dateTimeDTO = new DateTimeDTO();
        dateTimeDTO.setDateTime(LocalDateTime.now().minusDays(1).format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")));

        boolean result = toTest.makeAppointment(1L, dateTimeDTO, principal);

        assertFalse(result);
    }

    @Test
    public void testMakeAppointmentUserNotFound() {
        DateTimeDTO dateTimeDTO = new DateTimeDTO();
        dateTimeDTO.setDateTime(futureDateTime.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")));
        when(mockUserRepository.findByUsername(any())).thenReturn(Optional.empty());

        Assertions.assertThrows(UserNotFoundException.class, () ->
            toTest.makeAppointment(1L, dateTimeDTO, principal));
    }

    @Test
    public void testMakeAppointmentCourseNotFound() {
        DateTimeDTO dateTimeDTO = new DateTimeDTO();
        dateTimeDTO.setDateTime(futureDateTime.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")));
        when(mockUserRepository.findByUsername(any())).thenReturn(Optional.of(userEntity));
        when(mockCourseRepository.findById(any())).thenReturn(Optional.empty());

        Assertions.assertThrows(CourseNotFoundException.class, () -> {
            toTest.makeAppointment(1L, dateTimeDTO, principal);
        });
    }

    @Test
    public void testMakeAppointmentCourseOwnerTryingToMakeAppointment() {
        DateTimeDTO dateTimeDTO = new DateTimeDTO();
        dateTimeDTO.setDateTime(futureDateTime.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")));
        when(mockUserRepository.findByUsername(any())).thenReturn(Optional.of(userEntity));
        when(mockCourseRepository.findById(1L)).thenReturn(Optional.of(course));
        when(principal.getName()).thenReturn("testuser");

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            toTest.makeAppointment(1L, dateTimeDTO, principal);
        });
    }

    @Test
    public void testMakeAppointment_Success() throws UserNotFoundException, CourseNotFoundException {
        // Arrange
        DateTimeDTO dateTimeDTO = new DateTimeDTO();
        dateTimeDTO.setDateTime(futureDateTime.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")));
        when(mockUserRepository.findByUsername(any()))
                .thenReturn(Optional.of(userEntity));
        when(mockCourseRepository.findById(1L))
                .thenReturn(Optional.of(course));
        when(mockAppointmentRepository.existsByCourseIdAndDate(anyLong(), any())).thenReturn(false);
        when(principal.getName()).thenReturn("principalName");
        // Act
        boolean result = toTest.makeAppointment(1L, dateTimeDTO, principal);

        // Assert
        assertTrue(result);

        // Verify the appointment was created and saved
        ArgumentCaptor<Appointment> appointmentCaptor = ArgumentCaptor.forClass(Appointment.class);
        Mockito.verify(mockAppointmentRepository).save(appointmentCaptor.capture());

        Appointment capturedAppointment = appointmentCaptor.getValue();
        assertEquals(futureDateTime.truncatedTo(ChronoUnit.MINUTES), capturedAppointment.getDate());
        assertEquals(course, capturedAppointment.getCourse());
        assertEquals(userEntity, capturedAppointment.getUser());
        assertEquals("User principalName's appointment for Test Course", capturedAppointment.getDescription());

        Mockito.verify(mockUserRepository).save(userEntity);
        Mockito.verify(mockCourseRepository).save(course);
    }
}

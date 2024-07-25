package bg.softuni.tutorme.service.impl;

import bg.softuni.tutorme.entities.Appointment;
import bg.softuni.tutorme.entities.Course;
import bg.softuni.tutorme.entities.UserEntity;
import bg.softuni.tutorme.entities.dtos.AppointmentDetailDTO;
import bg.softuni.tutorme.entities.dtos.DateTimeDTO;
import bg.softuni.tutorme.entities.dtos.UserShortDTO;
import bg.softuni.tutorme.repositories.AppointmentRepository;
import bg.softuni.tutorme.repositories.CourseRepository;
import bg.softuni.tutorme.repositories.UserRepository;
import bg.softuni.tutorme.service.AppointmentService;
import bg.softuni.tutorme.service.exceptions.CourseNotFoundException;
import bg.softuni.tutorme.service.exceptions.UserNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final ModelMapper modelMapper;

    public AppointmentServiceImpl(AppointmentRepository appointmentRepository, UserRepository userRepository, CourseRepository courseRepository, ModelMapper modelMapper) {
        this.appointmentRepository = appointmentRepository;
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public boolean makeAppointment(long courseId, DateTimeDTO dateTimeDTO, Principal principal) throws UserNotFoundException, CourseNotFoundException {

        LocalDateTime parseDateTime = LocalDateTime.parse(dateTimeDTO.getDateTime(),
                DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));

        if (this.appointmentRepository.existsByCourseIdAndDate(courseId, parseDateTime)
                || !parseDateTime.isAfter(LocalDateTime.now())){
            return false;
        }

        UserEntity user = this.userRepository.findByUsername(principal.getName())
                .orElseThrow(UserNotFoundException::new);

        Course course = this.courseRepository.findById(courseId)
                .orElseThrow(CourseNotFoundException::new);

        if (course.getCourseOwner().getUsername().equals(principal.getName())){
            throw new IllegalArgumentException();
        }

        Appointment appointment = new Appointment()
                .setDate(parseDateTime)
                .setCourse(course)
                .setUser(user)
                .setDescription("User " + principal.getName() + "'s appointment for " + course.getTitle());

        user.getAppointments().add(appointment);
        course.getAppointments().add(appointment);

        this.appointmentRepository.save(appointment);
        this.userRepository.save(user);
        this.courseRepository.save(course);

        return true;
    }

    @Override
    @Transactional
    public AppointmentDetailDTO getAppointmentById(long id) {

        Appointment appointment = this.appointmentRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);//todo change exception

        return this.modelMapper.map(appointment, AppointmentDetailDTO.class)
                .setMadeBy(mapUserDTO(appointment.getUser()))
                .setCourseOwner(mapUserDTO(appointment.getCourse().getCourseOwner()));
    }
    private UserShortDTO mapUserDTO(UserEntity user) {
        return this.modelMapper.map(user, UserShortDTO.class)
                .setFullName(user.getName());
    }
}

package bg.softuni.tutorme.service.impl;

import bg.softuni.tutorme.entities.Appointment;
import bg.softuni.tutorme.entities.Course;
import bg.softuni.tutorme.entities.UserEntity;
import bg.softuni.tutorme.entities.dtos.appointment.AppointmentDTO;
import bg.softuni.tutorme.entities.dtos.tutor.TutorFeatureDTO;
import bg.softuni.tutorme.entities.dtos.user.UserProfileDTO;
import bg.softuni.tutorme.entities.dtos.courses.CourseShortInfoDTO;
import bg.softuni.tutorme.entities.enums.UserRoleEnum;
import bg.softuni.tutorme.entities.dtos.user.UserRegisterDTO;
import bg.softuni.tutorme.entities.user.TutorMeUserDetails;
import bg.softuni.tutorme.repositories.RoleRepository;
import bg.softuni.tutorme.repositories.UserRepository;
import bg.softuni.tutorme.service.UserEntityService;
import bg.softuni.tutorme.service.exceptions.UserNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserEntityServiceImpl implements UserEntityService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    private final ModelMapper modelMapper;

    public UserEntityServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean registerUser(UserRegisterDTO userRegisterDTO) {
        if (!userRegisterDTO.getPassword()
                .equals(userRegisterDTO.getConfirmPassword())){
            return false;
        }

        boolean isUsernameOrEmailTaken = this.userRepository.existsByUsernameOrEmail(
                userRegisterDTO.getUsername(),
                userRegisterDTO.getEmail());
        if (isUsernameOrEmailTaken){
            return false;
        }

        UserEntity user = mapToUser(userRegisterDTO);
        this.userRepository.save(user);
        return true;
    }

    private UserEntity mapToUser(UserRegisterDTO userRegisterDTO) {
        return new UserEntity()
                .setUsername(userRegisterDTO.getUsername())
                .setName(userRegisterDTO.getFullName())
                .setRoles(List.of(this.roleRepository.findByRole(UserRoleEnum.USER).get()))
                .setEmail(userRegisterDTO.getEmail())
                .setPassword(passwordEncoder
                        .encode(userRegisterDTO.getPassword()))
                .setFeatured(false);
    }

    @Override
    public String getUserIdByUsername(String username) throws UserNotFoundException {
        return this.userRepository.findByUsername(username)
                .orElseThrow(UserNotFoundException::new)
                .getId() + "";
    }

    @Override
    @Transactional
    public boolean isEnrolledInCourse(String username, long courseId) throws UserNotFoundException {
        return this.userRepository.findByUsername(username)
                .orElseThrow(UserNotFoundException::new)
                .getCoursesAttending()
                .stream()
                .anyMatch(course -> course.getId() == courseId);
    }

    @Override
    public Optional<TutorMeUserDetails> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null &&
                authentication.getPrincipal() instanceof TutorMeUserDetails tutorMeUserDetails) {
            return Optional.of(tutorMeUserDetails);
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public List<TutorFeatureDTO> getFeaturedTutors() {
        return this.userRepository.findAllByIsFeaturedTrue()
                .stream()
                .filter(u -> u.getRoles().stream().anyMatch(r -> r.getRole().name().equals("TUTOR")))
                .limit(3)
                .map(u -> new TutorFeatureDTO()
                        .setName(u.getName())
                        .setUsername(u.getUsername())
                        .setBiography(u.getBiography())
                        .setProfilePhotoUrl(u.getProfilePhotoUrl()))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public UserProfileDTO getUserByUsername(String username) throws UserNotFoundException {
        UserEntity user = this.userRepository.findByUsername(username)
                .orElseThrow(UserNotFoundException::new);

        return this.modelMapper.map(user, UserProfileDTO.class)
                .setCoursesAttending(mapCourses(user.getCoursesAttending()))
                .setCoursesTutoring(mapCourses(user.getCoursesTutoring()))
                .setAppointments(mapAppointments(user.getAppointments()));
    }
    private List<CourseShortInfoDTO> mapCourses(List<Course> courses){
        return courses
                .stream()
                .map(c -> this.modelMapper.map(c, CourseShortInfoDTO.class))
                .collect(Collectors.toList());
    }
    private List<AppointmentDTO> mapAppointments(List<Appointment> appointments) {
        return appointments
                .stream()
                .map(a -> this.modelMapper.map(a, AppointmentDTO.class))
                .collect(Collectors.toList());
    }


}

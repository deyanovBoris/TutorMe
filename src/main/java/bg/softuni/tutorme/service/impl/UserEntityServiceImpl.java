package bg.softuni.tutorme.service.impl;

import bg.softuni.tutorme.entities.UserEntity;
import bg.softuni.tutorme.entities.enums.UserRoleEnum;
import bg.softuni.tutorme.entities.dtos.UserRegisterDTO;
import bg.softuni.tutorme.entities.user.TutorMeUserDetails;
import bg.softuni.tutorme.repositories.CourseRepository;
import bg.softuni.tutorme.repositories.RoleRepository;
import bg.softuni.tutorme.repositories.UserRepository;
import bg.softuni.tutorme.service.UserEntityService;
import bg.softuni.tutorme.service.exceptions.UserNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserEntityServiceImpl implements UserEntityService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserEntityServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
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
}

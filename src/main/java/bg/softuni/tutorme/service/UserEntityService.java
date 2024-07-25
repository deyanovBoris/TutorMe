package bg.softuni.tutorme.service;

import bg.softuni.tutorme.entities.dtos.tutor.TutorFeatureDTO;
import bg.softuni.tutorme.entities.dtos.user.UserProfileDTO;
import bg.softuni.tutorme.entities.dtos.user.UserRegisterDTO;
import bg.softuni.tutorme.entities.user.TutorMeUserDetails;
import bg.softuni.tutorme.service.exceptions.UserNotFoundException;

import java.util.List;
import java.util.Optional;

public interface UserEntityService {
    boolean registerUser(UserRegisterDTO userRegisterDTO);

    String getUserIdByUsername(String username) throws UserNotFoundException;

    boolean isEnrolledInCourse(String username, long courseId) throws UserNotFoundException;

    Optional<TutorMeUserDetails> getCurrentUser();

    List<TutorFeatureDTO> getFeaturedTutors();

    UserProfileDTO getUserByUsername(String username) throws UserNotFoundException;
}

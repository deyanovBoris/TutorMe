package bg.softuni.tutorme.service;

import bg.softuni.tutorme.entities.dtos.UserRegisterDTO;
import bg.softuni.tutorme.service.exceptions.UserNotFoundException;

public interface UserEntityService {
    boolean registerUser(UserRegisterDTO userRegisterDTO);

    String getUserIdByUsername(String username) throws UserNotFoundException;

    boolean isEnrolledInCourse(String username, long courseId) throws UserNotFoundException;
}

package bg.softuni.tutorme.service;

import bg.softuni.tutorme.entities.dtos.tutor.TutorApplicationDTO;
import bg.softuni.tutorme.service.exceptions.ApplicationNotFoundException;
import bg.softuni.tutorme.service.exceptions.UserNotFoundException;

import java.util.List;

public interface TutorApplicationService {
    boolean submitTutorApplication(TutorApplicationDTO data);

    boolean hasPendingApplication(String username);

    boolean approveApplication(long applicationId) throws UserNotFoundException, ApplicationNotFoundException;
    List<TutorApplicationDTO> getAllApplications();

    boolean rejectApplication(long applicationId);

    boolean deleteApplication(long applicationId);
}

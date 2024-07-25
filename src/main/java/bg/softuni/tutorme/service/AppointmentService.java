package bg.softuni.tutorme.service;

import bg.softuni.tutorme.entities.dtos.appointment.AppointmentDetailDTO;
import bg.softuni.tutorme.entities.dtos.DateTimeDTO;
import bg.softuni.tutorme.service.exceptions.CourseNotFoundException;
import bg.softuni.tutorme.service.exceptions.UserNotFoundException;

import java.security.Principal;

public interface AppointmentService {
    boolean makeAppointment(long courseId, DateTimeDTO dateTimeDTO, Principal principal) throws UserNotFoundException, CourseNotFoundException;

    AppointmentDetailDTO getAppointmentById(long id);

    void deleteAppointment(long id);
}

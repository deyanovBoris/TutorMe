package bg.softuni.tutorme.service.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class AppointmentNotFoundException extends Exception{

    public AppointmentNotFoundException(long appointmentId){
        super("Appointment with id " + appointmentId + " not found.");
    }
}

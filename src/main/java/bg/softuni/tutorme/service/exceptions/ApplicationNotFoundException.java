package bg.softuni.tutorme.service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Application does not exist")
public class ApplicationNotFoundException extends Exception{


}

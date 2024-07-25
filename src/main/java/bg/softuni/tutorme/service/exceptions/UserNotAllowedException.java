package bg.softuni.tutorme.service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN,
        reason = "You are not allowed to view this page")
public class UserNotAllowedException extends RuntimeException {
    private static final long serialVersionUID = 1L;
}
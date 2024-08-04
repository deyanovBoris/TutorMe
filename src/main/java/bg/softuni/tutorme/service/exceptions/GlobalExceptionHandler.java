package bg.softuni.tutorme.service.exceptions;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleUserNotFoundException(UserNotFoundException ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        return "error/404";
    }

    @ExceptionHandler(CourseNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleCourseNotFoundException(CourseNotFoundException ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        return "error/404";
    }

    @ExceptionHandler(UserNotAllowedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String handleUserNotAllowedException(UserNotAllowedException ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        return "error/403";
    }

    @ExceptionHandler(ApplicationNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleApplicationNotFoundException(ApplicationNotFoundException ex, Model model){
        model.addAttribute("errorMessage", ex.getMessage());
        return "error/404";
    }
}


package bg.softuni.tutorme.web;

import bg.softuni.tutorme.entities.dtos.TutorFeatureDTO;
import bg.softuni.tutorme.entities.dtos.subjects.SubjectFeatureDTO;
import bg.softuni.tutorme.repositories.SubjectRepository;
import bg.softuni.tutorme.service.SubjectService;
import bg.softuni.tutorme.service.UserEntityService;
import bg.softuni.tutorme.service.exceptions.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.security.Principal;
import java.util.List;

@Controller
public class HomeController {
    private final UserEntityService userEntityService;
    private final SubjectService subjectService;

    public HomeController(UserEntityService userEntityService,
                          SubjectService subjectService,
                          SubjectRepository subjectRepository) {
        this.userEntityService = userEntityService;
        this.subjectService = subjectService;
    }

    @GetMapping("/user/{id}")
//    @PreAuthorize("hasRole('ADMIN')")
    public String dashboard(@PathVariable("id") String id, Model model, Principal principal) throws UserNotFoundException {

//        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.getName();
        }

        // Retrieve the user's ID from the username (replace with your method)
        String userId = this.userEntityService.getUserIdByUsername(username);

        // Check if the requested user ID matches the logged-in user's ID
        if (!id.equals(userId)) {
            // If not, return a 403 Forbidden page
            throw new UserNotAllowedException();
        }

        model.addAttribute("userId", id);

        return "profile";
    }

//    @ResponseStatus(code = HttpStatus.FORBIDDEN)
//    @ExceptionHandler(UserNotAllowedException.class)
//    public String handleAttempt(){
//
//    }


    @ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "You are not allowed to view this page")
    public class UserNotAllowedException extends RuntimeException {
        private static final long serialVersionUID = 1L;
    }

    @GetMapping("/")
    public String index(Model model){
        List<SubjectFeatureDTO> featuredSubjects = this.subjectService.getFeaturedSubjects();
        List<TutorFeatureDTO> tutorFeatureDTOS = this.userEntityService.getFeaturedTutors();

        model.addAttribute("featuredSubjects",  featuredSubjects);
        model.addAttribute("featuredTutors", tutorFeatureDTOS);

        return "index";
    }

    @GetMapping("/access-denied")
    public String accessDenied(){
        return "403";
    }

}

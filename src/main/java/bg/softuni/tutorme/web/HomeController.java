package bg.softuni.tutorme.web;

import bg.softuni.tutorme.entities.dtos.quotes.QuoteOutputDTO;
import bg.softuni.tutorme.entities.dtos.tutor.TutorFeatureDTO;
import bg.softuni.tutorme.entities.dtos.subjects.SubjectFeatureDTO;
import bg.softuni.tutorme.repositories.SubjectRepository;
import bg.softuni.tutorme.service.QuoteService;
import bg.softuni.tutorme.service.SubjectService;
import bg.softuni.tutorme.service.UserEntityService;
import bg.softuni.tutorme.service.exceptions.UserNotAllowedException;
import bg.softuni.tutorme.service.exceptions.UserNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;
import java.util.List;

@Controller
public class HomeController {
    private final UserEntityService userEntityService;
    private final SubjectService subjectService;
    private final QuoteService quoteService;

    public HomeController(UserEntityService userEntityService,
                          SubjectService subjectService,
                          QuoteService quoteService) {
        this.userEntityService = userEntityService;
        this.subjectService = subjectService;
        this.quoteService = quoteService;
    }

    @GetMapping("/user/{username}")
    public String dashboard(@PathVariable("username") String username, Model model, Principal principal) throws UserNotFoundException {

        String usernamePrincipal;
        if (principal instanceof UserDetails) {
            usernamePrincipal = ((UserDetails) principal).getUsername();
        } else {
            usernamePrincipal = principal.getName();
        }

        if (!usernamePrincipal.equals(username)) {
            throw new UserNotAllowedException();
        }

        QuoteOutputDTO randomQuote = this.quoteService.getRandomQuote();

        model.addAttribute("userData", this.userEntityService.getUserByUsername(username));
        model.addAttribute("profilePageStyle", true);
        model.addAttribute("randomQuote", randomQuote);

        return "profile";
    }

    @GetMapping("/")
    public String index(Model model){
        List<SubjectFeatureDTO> featuredSubjects = this.subjectService.getFeaturedSubjects();
        List<TutorFeatureDTO> tutorFeatureDTOS = this.userEntityService.getFeaturedTutors();

        model.addAttribute("homePageStyles", true);
        model.addAttribute("featuredSubjects",  featuredSubjects);
        model.addAttribute("featuredTutors", tutorFeatureDTOS);

        return "index";
    }
}

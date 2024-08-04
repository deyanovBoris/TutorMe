package bg.softuni.tutorme.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorController {

    @GetMapping("/access-denied")
    public String getAccessDenied(){
        return "/error/403";
    }
}

package bg.softuni.tutorme.web;

import bg.softuni.tutorme.entities.dtos.UserLoginDTO;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {
    @ModelAttribute("loginUserData")
    public UserLoginDTO userLoginDTO(){
        return new UserLoginDTO();
    }

    @GetMapping("/login")
    public String login(){

        return "login";
    }


    @GetMapping("/login/login-error")
    public String loginError(Model model,
                             @Valid UserLoginDTO data,
                             BindingResult bindingResult,
                             RedirectAttributes rAtt){
        if (bindingResult.hasErrors()){
            rAtt.addFlashAttribute("loginUserData", data);
            rAtt.addFlashAttribute(
                    "org.springframework.validation.BindingResult.loginUserData",
                    bindingResult);
        }

        model.addAttribute("showErrorMessage", true);
        return "login";
    }

}

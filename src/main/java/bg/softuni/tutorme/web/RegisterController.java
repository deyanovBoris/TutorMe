package bg.softuni.tutorme.web;

import bg.softuni.tutorme.entities.dtos.user.UserRegisterDTO;
import bg.softuni.tutorme.service.UserEntityService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class RegisterController {

    private final UserEntityService userEntityService;

    public RegisterController(UserEntityService userEntityService) {
        this.userEntityService = userEntityService;
    }


    @ModelAttribute("userRegisterObject")
    public UserRegisterDTO userRegisterDTO(){
        return new UserRegisterDTO();
    }

    @GetMapping("/register")
    public String register(){
        return "register";
    }

    @PostMapping("/register")
    public String register(@Valid UserRegisterDTO data,
                           BindingResult bindingResult,
                           RedirectAttributes rAtt){


        if (bindingResult.hasErrors()){
            rAtt.addFlashAttribute("userRegisterObject", data);
            rAtt.addFlashAttribute(
                    "org.springframework.validation.BindingResult.userRegisterObject",
                    bindingResult);

            return "redirect:/register";
        }

        boolean success = this.userEntityService.registerUser(data);

        if (!success){
            rAtt.addFlashAttribute("userRegisterObject", data);
            rAtt.addFlashAttribute("passwordsDoNotMatch", true);

            return "redirect:/register";
        }

        return "redirect:/login";
    }
}

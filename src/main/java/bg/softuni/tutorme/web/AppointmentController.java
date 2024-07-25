package bg.softuni.tutorme.web;

import bg.softuni.tutorme.entities.dtos.AppointmentDetailDTO;
import bg.softuni.tutorme.service.AppointmentService;
import bg.softuni.tutorme.service.exceptions.UserNotAllowedException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;

@Controller
public class AppointmentController {

    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @GetMapping("/appointment/{id}")
    public String getAppointment(@PathVariable("id") long id,
                                 Model model,
                                 Principal principal){

        AppointmentDetailDTO appointmentById = this.appointmentService.getAppointmentById(id);
        String username = principal.getName();
        if (!username.equals(appointmentById.getMadeBy().getUsername())
                && !username.equals(appointmentById.getCourseOwner().getUsername())){
            throw new UserNotAllowedException();
        }

        model.addAttribute("appointmentPageStyling", true);
        model.addAttribute("appointmentData", appointmentById);

        return "appointment";
    }
}

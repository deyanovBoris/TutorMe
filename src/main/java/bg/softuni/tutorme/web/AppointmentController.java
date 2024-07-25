package bg.softuni.tutorme.web;

import bg.softuni.tutorme.service.AppointmentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class AppointmentController {

    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @GetMapping("/appointment/{id}")
    public String getAppointment(@PathVariable("id") long id,
                                 Model model){
        model.addAttribute("appointmentPageStyling", true);
        model.addAttribute("appointmentData", this.appointmentService.getAppointmentById(id));

        return "appointment";
    }
}

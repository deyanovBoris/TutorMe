package bg.softuni.tutorme.web;

import bg.softuni.tutorme.entities.dtos.appointment.AppointmentDetailDTO;
import bg.softuni.tutorme.entities.dtos.DateTimeDTO;
import bg.softuni.tutorme.service.AppointmentService;
import bg.softuni.tutorme.service.exceptions.AppointmentNotFoundException;
import bg.softuni.tutorme.service.exceptions.CourseNotFoundException;
import bg.softuni.tutorme.service.exceptions.UserNotAllowedException;
import bg.softuni.tutorme.service.exceptions.UserNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
                                 Principal principal) throws AppointmentNotFoundException {

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

    @PostMapping("/course/make-appointment/{courseId}")
    public String makeAppointment(@PathVariable("courseId") long courseId,
                                  Model model,
                                  DateTimeDTO dateTimeDTO,
                                  RedirectAttributes rAtt,
                                  Principal principal)
            throws UserNotFoundException, CourseNotFoundException {

        boolean success = this.appointmentService.makeAppointment(courseId, dateTimeDTO, principal);

        if (!success){
            rAtt.addFlashAttribute("dateTimeObject", dateTimeDTO);
            rAtt.addFlashAttribute("errorDt", true);
            return "redirect:/course/{courseId}";
        }
        rAtt.addFlashAttribute("dateTimeObjectSuccess", dateTimeDTO);
        rAtt.addFlashAttribute("successApt", true);
        return "redirect:/course/{courseId}";
    }

    @DeleteMapping("/appointment/delete/{id}")
    public String deleteAppointment(@PathVariable("id") long id,
                                    Principal principal) throws AppointmentNotFoundException {

        AppointmentDetailDTO appointmentById = this.appointmentService.getAppointmentById(id);
        String username = principal.getName();
        if (!username.equals(appointmentById.getMadeBy().getUsername())
                && !username.equals(appointmentById.getCourseOwner().getUsername())){
            throw new UserNotAllowedException();
        }

        this.appointmentService.deleteAppointment(id);

        return "redirect:/";
    }
}

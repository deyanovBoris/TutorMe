package bg.softuni.tutorme.web;

import bg.softuni.tutorme.entities.dtos.TutorApplicationDTO;
import bg.softuni.tutorme.entities.dtos.subjects.SubjectFeatureDTO;
import bg.softuni.tutorme.service.SubjectService;
import bg.softuni.tutorme.service.TutorApplicationService;
import bg.softuni.tutorme.service.exceptions.UserNotFoundException;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
public class TutorController {

    private final SubjectService subjectService;
    private final TutorApplicationService tutorApplicationService;

    public TutorController(SubjectService subjectService, TutorApplicationService tutorApplicationService) {
        this.subjectService = subjectService;
        this.tutorApplicationService = tutorApplicationService;
    }

    @ModelAttribute("applicationData")
    public TutorApplicationDTO tutorApplicationDTO(){
        return new TutorApplicationDTO();
    }

    @ModelAttribute("subjectList")
    public List<SubjectFeatureDTO> subjectsList(){
        return this.subjectService.getSubjects();
    }

    @GetMapping("/start-tutoring")
    public String startTutoring(){
        return "start-tutoring";
    }

    @GetMapping("/start-tutoring/apply")
    public String startTutoringApply(Model model, Principal principal){

        if (this.tutorApplicationService.hasPendingApplication(principal.getName())){
            model.addAttribute("applicationStillPending", true);
        }

        return "start-tutoring-form";
    }

    @PostMapping("/start-tutoring/apply")
    public String submitApplication(Model model,
                                    @Valid TutorApplicationDTO tutorData,
                                    BindingResult bindingResult,
                                    RedirectAttributes rAtt,
                                    Principal principal){
            if (this.tutorApplicationService.hasPendingApplication(principal.getName())){
                rAtt.addFlashAttribute("applicationStillPending", true);
                return "redirect:/start-tutoring/apply";
            }

            if (bindingResult.hasErrors()){
                rAtt.addFlashAttribute("applicationData", tutorData);
                rAtt.addFlashAttribute("org.springframework.validation.BindingResult.applicationData",
                        bindingResult);

                return "redirect:/start-tutoring/apply";
            }
            tutorData.setApplicantUsername(principal.getName());

            boolean success = tutorApplicationService.submitTutorApplication(tutorData);

            if (!success){
                return "redirect:/start-tutoring/apply";
            }

            rAtt.addFlashAttribute("successfulApplication", true);

            return "redirect:/start-tutoring/apply";
    }

    @GetMapping("/admin/tutor-applications")
    public String tutorApplications(Model model){

        model.addAttribute("tutorApplications", this.tutorApplicationService.getAllApplications());

        return "admin-tutor-applications";
    }

    //ADMIN Rights
    //Todo make sure that only admin can do this in sec config
    @PostMapping("/admin/approve-tutor-application/{id}")
    public String approveTutorApplication(@PathVariable("id") long applicationId) throws UserNotFoundException {
        //todo make sure an application cannot be approved when rejected

        this.tutorApplicationService.approveApplication(applicationId);

        return "redirect:/admin/tutor-applications";
    }

    @PostMapping("/admin/reject-tutor-application/{id}")
    public String rejectTutorApplication(@PathVariable("id") long applicationId) {
        //todo make sure an application cannot be rejected once approved
        this.tutorApplicationService.rejectApplication(applicationId);

        return "redirect:/admin/tutor-applications";
    }

    @DeleteMapping("/admin/delete-tutor-application/{id}")
    public String deleteTutorApplication(@PathVariable("id") long applicationId) {

        this.tutorApplicationService.deleteApplication(applicationId);

        return "redirect:/admin/tutor-applications";
    }
}

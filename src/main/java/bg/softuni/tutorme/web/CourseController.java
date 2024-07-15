package bg.softuni.tutorme.web;

import bg.softuni.tutorme.entities.dtos.courses.CourseAddDTO;
import bg.softuni.tutorme.entities.dtos.courses.CourseShortInfoDTO;
import bg.softuni.tutorme.entities.enums.CourseType;
import bg.softuni.tutorme.service.CourseService;
import bg.softuni.tutorme.service.SubjectService;
import bg.softuni.tutorme.service.exceptions.UserNotFoundException;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
public class CourseController {

    private final SubjectService subjectService;
    private final CourseService courseService;

    public CourseController(SubjectService subjectService, CourseService courseService) {
        this.subjectService = subjectService;
        this.courseService = courseService;
    }

    @ModelAttribute("applicationData")
    public CourseAddDTO courseAddDTO(){
        return new CourseAddDTO();
    }

    @GetMapping("/courses/all/{pageNo}")
    public String getAllCourses(@PathVariable("pageNo") int pageNo, Model model){

        Page<CourseShortInfoDTO> page = this.courseService.findPaginated(pageNo, 8);
        List<CourseShortInfoDTO> courses = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems" , page.getTotalElements());
        model.addAttribute("courses", courses);

//        model.addAttribute("courses", this.courseService.getAllCourses());
        return "courses";
    }

    @GetMapping("/courses/all/add")
    public String addCourse(Model model){
        model.addAttribute("subjects", this.subjectService.getSubjects());
        model.addAttribute("courseTypes", CourseType.values());

        return "courses-add";
    }

    @PostMapping("/courses/all/add")
    public String doAddCourse(Model model,
                            @Valid CourseAddDTO courseData,
                            BindingResult bindingResult,
                            RedirectAttributes rAtt,
                            Principal principal) throws UserNotFoundException {

        if (bindingResult.hasErrors()){
            rAtt.addFlashAttribute("applicationData", courseData);
            rAtt.addFlashAttribute("org.springframework.validation.BindingResult.applicationData",
                    bindingResult);

            return "redirect:/courses/add";
        }

        courseData.setOwnerUsername(principal.getName());
        this.courseService.submitCourse(courseData);

        return "redirect:/courses";
    }

    @GetMapping("/courses/subject/{id}")
    public String coursesBySubjectId(@PathVariable("id") long id, Model model){
        model.addAttribute("courses", this.courseService.getAllCoursesBySubjectId(id));
        model.addAttribute("subject", this.subjectService.getSubjectNameById(id));

        return "courses-by-subject";
    }

    @GetMapping("/course/{id}")
    public String courseById(@PathVariable("id") long id, Model model){
        model.addAttribute("course", this.courseService.getCourseById(id));
        return "course";
    }
}

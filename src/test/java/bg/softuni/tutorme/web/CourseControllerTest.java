package bg.softuni.tutorme.web;

import bg.softuni.tutorme.entities.Subject;
import bg.softuni.tutorme.entities.dtos.InstructorDTO;
import bg.softuni.tutorme.entities.dtos.StudentsShortInfoDto;
import bg.softuni.tutorme.entities.dtos.appointment.AppointmentCourseDTO;
import bg.softuni.tutorme.entities.dtos.courses.CourseAddDTO;
import bg.softuni.tutorme.entities.dtos.courses.CourseInfoDTO;
import bg.softuni.tutorme.entities.dtos.courses.CourseShortInfoDTO;
import bg.softuni.tutorme.entities.dtos.subjects.SubjectFeatureDTO;
import bg.softuni.tutorme.entities.enums.CourseType;
import bg.softuni.tutorme.service.CourseService;
import bg.softuni.tutorme.service.SubjectService;
import bg.softuni.tutorme.service.UserEntityService;
import bg.softuni.tutorme.service.exceptions.CourseNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.validation.BindingResult;

import java.security.Principal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CourseControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CourseService mockCourseService;
    @MockBean
    private UserEntityService mockUserEntityService;
    @MockBean
    private SubjectService mockSubjectService;

    @Test
    public void testGetAllCourses() throws Exception {
        // Arrange
        List<CourseShortInfoDTO> courseList = Arrays.asList(
                new CourseShortInfoDTO().setId(1L).setTitle("Course 1"),
                new CourseShortInfoDTO().setId(2L).setTitle("Course 2")
        );
        Page<CourseShortInfoDTO> coursePage = new PageImpl<>(courseList,
                PageRequest.of(0, 8), 2);

        when(mockCourseService.findPaginated(anyInt(), anyInt())).thenReturn(coursePage);

        // Act & Assert
        mockMvc.perform(get("/courses/all/{pageNo}", 1))
                .andExpect(status().isOk())
                .andExpect(view().name("courses"))
                .andExpect(model().attribute("coursesPageStyling", true))
                .andExpect(model().attribute("currentPage", 1))
                .andExpect(model().attribute("totalPages", coursePage.getTotalPages()))
                .andExpect(model().attribute("totalItems", coursePage.getTotalElements()))
                .andExpect(model().attribute("courses", courseList))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @WithMockUser(username = "user", roles = {"TUTOR"})
    public void testDoAddCourseValidationErrors() throws Exception {
        CourseAddDTO courseData = new CourseAddDTO();
        BindingResult bindingResult = mock(BindingResult.class);
        Principal principal = mock(Principal.class);

        when(principal.getName()).thenReturn("testUser");
        when(bindingResult.hasErrors()).thenReturn(true);
        when(bindingResult.getFieldErrors()).thenReturn(Collections.emptyList());

        mockMvc.perform(post("/courses/all/add")
                        .principal(principal)
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .flashAttr("courseData", courseData)
                        .flashAttr("org.springframework.validation.BindingResult.courseData", bindingResult))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/courses/add"))
                .andExpect(flash().attributeExists("applicationData"))
                .andExpect(flash().attributeExists("org.springframework.validation.BindingResult.applicationData"));

        verify(mockCourseService, times(0)).submitCourse(any(CourseAddDTO.class));
    }

    @Test
    @WithMockUser(username = "user", roles = {"TUTOR"})
    public void testGetAddCourse() throws Exception {
        when(mockSubjectService.getSubjects()).thenReturn(List.of(new SubjectFeatureDTO().setName("Math"),
                new SubjectFeatureDTO().setName("Science")));

        mockMvc.perform(get("/courses/all/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("courses-add"))
                .andExpect(model().attributeExists("subjects"))
                .andExpect(model().attribute("subjects", hasSize(2)))
                .andExpect(model().attributeExists("courseTypes"))
                .andExpect(model().attribute("courseTypes", is(CourseType.values())));
    }

    @Test
    @WithMockUser(username = "user", roles = {"TUTOR"})
    public void testDoAddCourseSuccessfulAddition() throws Exception {
        CourseAddDTO courseData = new CourseAddDTO()
                .setSubjects(List.of(1L, 2L, 3L))
                .setCourseType(CourseType.GROUP)
                .setCourseImageUrl("https://example.com")
                .setTitle("Course Name")
                .setStartDate(LocalDate.of(2024, 10, 10))
                .setEndDate(LocalDate.of(2024, 10, 30))
                .setDescription("Course Description")
                .setOwnerUsername("user");

        Principal principal = mock(Principal.class);

        when(principal.getName()).thenReturn("testUser");

        mockMvc.perform(post("/courses/all/add")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .param("title", "Course Name")
                        .param("subjects", "1,2,3")
                        .param("courseType", CourseType.GROUP.name())
                        .param("startDate", LocalDate.of(2024, 10, 10).toString())
                        .param("endDate", LocalDate.of(2024, 10, 30).toString())
                        .param("description", "Course Description")
                        .param("courseImageUrl", "https://example.com")
                        .principal(principal))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/courses"));

        verify(mockCourseService).submitCourse(argThat(course ->
                "Course Name".equals(course.getTitle()) &&
                "Course Description".equals(course.getDescription()) &&
                "user".equals(course.getOwnerUsername())
        ));
    }

    @Test
    @WithMockUser(username = "user")
    public void testCourseByIdUserEnrolled() throws Exception {
        // Arrange
        long courseId = 1L;
        String username = "user";
        Principal principal = () -> username;

        CourseInfoDTO courseDTO = new CourseInfoDTO()
                .setId(courseId)
                .setTitle("Course Title")
                .setDescription("Course Description")
                // add all below due to Tyhmeleaf integration
                .setInstructor(new InstructorDTO().setName("testInstructor"))
                .setStudents(List.of(new StudentsShortInfoDto().setName("testStudent1"),
                        new StudentsShortInfoDto().setName("testStudent2")))
                .setAppointments(List.of(new AppointmentCourseDTO()));

        when(mockCourseService.getCourseById(courseId)).thenReturn(courseDTO);
        when(mockUserEntityService.isEnrolledInCourse(username, courseId)).thenReturn(true);
        when(mockCourseService.isCourseOwner(username, courseId)).thenReturn(false);

        // Act & Assert
        mockMvc.perform(get("/course/{id}", courseId).principal(principal))
                .andExpect(status().isOk())
                .andExpect(view().name("course"))
                .andExpect(model().attribute("coursePageStyling", true))
                .andExpect(model().attribute("course", courseDTO))
                .andExpect(model().attribute("isAlreadyEnrolled", true))
                .andExpect(model().attribute("isCourseOwner", false))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @WithMockUser(username = "user")
    public void testCourseByIdUserIsOwner() throws Exception {
        // Arrange
        long courseId = 1L;
        String username = "user";
        Principal principal = () -> username;

        CourseInfoDTO courseDTO = new CourseInfoDTO()
                .setId(courseId)
                .setTitle("Course Title")
                .setDescription("Course Description")
                // add all below due to Tyhmeleaf integration
                .setInstructor(new InstructorDTO().setName("testInstructor"))
                .setStudents(List.of(new StudentsShortInfoDto().setName("testStudent1"),
                        new StudentsShortInfoDto().setName("testStudent2")))
                .setAppointments(List.of(new AppointmentCourseDTO()));

        when(mockCourseService.getCourseById(courseId)).thenReturn(courseDTO);
        when(mockUserEntityService.isEnrolledInCourse(username, courseId)).thenReturn(false);
        when(mockCourseService.isCourseOwner(username, courseId)).thenReturn(true);

        // Act & Assert
        mockMvc.perform(get("/course/{id}", courseId).principal(principal))
                .andExpect(status().isOk())
                .andExpect(view().name("course"))
                .andExpect(model().attribute("coursePageStyling", true))
                .andExpect(model().attribute("course", courseDTO))
                .andExpect(model().attribute("isAlreadyEnrolled", true))
                .andExpect(model().attribute("isCourseOwner", true))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @WithMockUser(username = "user")
    public void testCourseByIdCourseNotFound() throws Exception {
        long courseId = 1L;

        when(mockCourseService.getCourseById(courseId)).thenThrow(new CourseNotFoundException(courseId));

        mockMvc.perform(get("/course/{id}", courseId))
                .andExpect(status().isNotFound())
                .andExpect(view().name("error/404"))
                .andExpect(model().attributeExists("errorMessage"));
    }

    @Test
    @WithMockUser(username = "user")
    public void testEnrollInCourseSuccessfulEnrollment() throws Exception {
        long courseId = 1L;
        String username = "user";
        Principal principal = mock(Principal.class);

        when(principal.getName()).thenReturn(username);
        when(mockUserEntityService.isEnrolledInCourse(username, courseId)).thenReturn(false);
        when(mockCourseService.isCourseOwner(username, courseId)).thenReturn(false);
        when(mockCourseService.enrollInCourse(username, courseId)).thenReturn(true);

        mockMvc.perform(post("/course/enroll/{id}", courseId)
                        .principal(principal)
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/course/" + courseId));

        verify(mockCourseService).enrollInCourse(username, courseId);
    }

    @Test
    @WithMockUser(username = "user")
    public void testEnrollInCourseUserAlreadyEnrolled() throws Exception {
        long courseId = 1L;
        String username = "user";
        Principal principal = mock(Principal.class);

        when(principal.getName()).thenReturn(username);
        when(mockUserEntityService.isEnrolledInCourse(username, courseId)).thenReturn(true);
        when(mockCourseService.isCourseOwner(username, courseId)).thenReturn(false);

        mockMvc.perform(post("/course/enroll/{id}", courseId)
                        .principal(principal)
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/course/" + courseId));

        verify(mockCourseService, times(0)).enrollInCourse(anyString(), anyLong());
    }

    @Test
    @WithMockUser(username = "user")
    public void testEnrollInCourseUserIsCourseOwner() throws Exception {
        long courseId = 1L;
        String username = "user";
        Principal principal = mock(Principal.class);

        when(principal.getName()).thenReturn(username);
        when(mockUserEntityService.isEnrolledInCourse(username, courseId)).thenReturn(false);
        when(mockCourseService.isCourseOwner(username, courseId)).thenReturn(true);

        mockMvc.perform(post("/course/enroll/{id}", courseId)
                        .principal(principal)
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/course/" + courseId));

        verify(mockCourseService, times(0)).enrollInCourse(anyString(), anyLong());
    }
}

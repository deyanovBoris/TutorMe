package bg.softuni.tutorme.web;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import bg.softuni.tutorme.entities.dtos.DateTimeDTO;
import bg.softuni.tutorme.entities.dtos.appointment.AppointmentDetailDTO;
import bg.softuni.tutorme.entities.dtos.user.UserShortDTO;
import bg.softuni.tutorme.service.AppointmentService;
import bg.softuni.tutorme.service.exceptions.AppointmentNotFoundException;
import bg.softuni.tutorme.service.exceptions.CourseNotFoundException;
import bg.softuni.tutorme.service.exceptions.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class AppointmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AppointmentService mockAppointmentService;

    @BeforeEach
    public void setUp() throws AppointmentNotFoundException {
        AppointmentDetailDTO appointmentDetail = new AppointmentDetailDTO();

        when(mockAppointmentService.getAppointmentById(anyLong()))
                .thenReturn(appointmentDetail);
    }

    @Test
    @WithMockUser(username = "user1")
    public void testGetAppointmentSuccess() throws Exception {

        long appointmentId = 1L;
        AppointmentDetailDTO appointmentDetail = new AppointmentDetailDTO();
        appointmentDetail.setMadeBy(new UserShortDTO().setUsername("user1"));
        appointmentDetail.setCourseOwner(new UserShortDTO().setUsername("courseOwner"));
        when(mockAppointmentService.getAppointmentById(appointmentId)).thenReturn(appointmentDetail);

        mockMvc.perform(MockMvcRequestBuilders.get("/appointment/{id}", appointmentId))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("appointment"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("appointmentPageStyling"))
                .andExpect(MockMvcResultMatchers.model().attribute("appointmentPageStyling", true))
                .andExpect(MockMvcResultMatchers.model().attributeExists("appointmentData"))
                .andExpect(MockMvcResultMatchers.model().attribute("appointmentData", appointmentDetail))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @WithMockUser(username = "user2")
    public void testGetAppointmentUserNotAllowed() throws Exception {
        // Arrange
        long appointmentId = 1L;
        AppointmentDetailDTO appointmentDetail = new AppointmentDetailDTO();
        appointmentDetail.setMadeBy(new UserShortDTO().setUsername("user1"));
        appointmentDetail.setCourseOwner(new UserShortDTO().setUsername("courseOwner"));
        when(mockAppointmentService.getAppointmentById(appointmentId)).thenReturn(appointmentDetail);

        // Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/appointment/{id}", appointmentId))
                .andExpect(status().isForbidden())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @WithMockUser(username = "user")
    public void testDeleteAppointment_Success() throws Exception {
        long appointmentId = 1L;
        String username = "user";

        AppointmentDetailDTO appointmentDTO = new AppointmentDetailDTO();
        appointmentDTO.setMadeBy(new UserShortDTO().setUsername(username));
        appointmentDTO.setCourseOwner(new UserShortDTO().setUsername("otheruser"));

        when(mockAppointmentService.getAppointmentById(appointmentId)).thenReturn(appointmentDTO);

        mockMvc.perform(delete("/appointment/delete/{id}", appointmentId)
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }

    @Test
    @WithMockUser(username = "user")
    public void testDeleteAppointment_UserNotAllowed() throws Exception {
        long appointmentId = 1L;
        String username = "user";

        AppointmentDetailDTO appointmentDTO = new AppointmentDetailDTO();
        appointmentDTO.setMadeBy(new UserShortDTO().setUsername(username + "notLoggedIn"));
        appointmentDTO.setCourseOwner(new UserShortDTO().setUsername("differentUser"));

        when(mockAppointmentService.getAppointmentById(appointmentId)).thenReturn(appointmentDTO);

        mockMvc.perform(delete("/appointment/delete/{id}", appointmentId)
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "user")
    public void testDeleteAppointment_AppointmentNotFound() throws Exception {
        long appointmentId = 1L;

        when(mockAppointmentService.getAppointmentById(appointmentId)).thenThrow(new AppointmentNotFoundException(appointmentId));

        mockMvc.perform(delete("/appointment/delete/{id}", appointmentId)
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "user")
    public void testMakeAppointment_Success() throws Exception {
        long courseId = 1L;
        DateTimeDTO dateTimeDTO = new DateTimeDTO();
        dateTimeDTO.setDateTime("01-01-2025 10:00");

        when(mockAppointmentService.makeAppointment(anyLong(), any(DateTimeDTO.class), any()))
                .thenReturn(true);

        mockMvc.perform(post("/course/make-appointment/{courseId}", courseId)
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .param("dateTime", dateTimeDTO.getDateTime()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/course/" + courseId))
                .andExpect(flash().attributeExists("dateTimeObjectSuccess"))
                .andExpect(flash().attribute("successApt", true));
    }

    @Test
    @WithMockUser(username = "user")
    public void testMakeAppointment_Failure() throws Exception {
        long courseId = 1L;
        DateTimeDTO dateTimeDTO = new DateTimeDTO();
        dateTimeDTO.setDateTime("01-01-2025 10:00");

        when(mockAppointmentService.makeAppointment(anyLong(), any(DateTimeDTO.class), any()))
                .thenReturn(false);

        mockMvc.perform(post("/course/make-appointment/{courseId}", courseId)
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .param("dateTime", dateTimeDTO.getDateTime()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/course/" + courseId))
                .andExpect(flash().attributeExists("dateTimeObject"))
                .andExpect(flash().attribute("errorDt", true));
    }

    @Test
    @WithMockUser(username = "user")
    public void testMakeAppointment_UserNotFoundException() throws Exception {
        long courseId = 1L;
        DateTimeDTO dateTimeDTO = new DateTimeDTO();
        dateTimeDTO.setDateTime("01-01-2025 10:00");

        when(mockAppointmentService.makeAppointment(anyLong(), any(DateTimeDTO.class), any()))
                .thenThrow(new UserNotFoundException());

        mockMvc.perform(post("/course/make-appointment/{courseId}", courseId)
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .param("dateTime", dateTimeDTO.getDateTime()))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "user")
    public void testMakeAppointment_CourseNotFoundException() throws Exception {
        long courseId = 1L;
        DateTimeDTO dateTimeDTO = new DateTimeDTO();
        dateTimeDTO.setDateTime("01-01-2025 10:00");

        when(mockAppointmentService.makeAppointment(anyLong(), any(DateTimeDTO.class), any()))
                .thenThrow(new CourseNotFoundException(courseId));

        mockMvc.perform(post("/course/make-appointment/{courseId}", courseId)
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .param("dateTime", dateTimeDTO.getDateTime()))
                .andExpect(status().isNotFound());
    }
}


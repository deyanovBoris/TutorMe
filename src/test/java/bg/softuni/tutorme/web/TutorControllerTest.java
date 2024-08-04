package bg.softuni.tutorme.web;

import bg.softuni.tutorme.entities.dtos.tutor.TutorApplicationDTO;
import bg.softuni.tutorme.service.TutorApplicationService;
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

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class TutorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TutorApplicationService tutorApplicationService;

    @BeforeEach
    public void setUp() {
        // Initialize mocks if necessary
    }

    @Test
    @WithMockUser(username = "user1")
    public void testSubmitApplication_PendingApplication() throws Exception {
        // Arrange
        when(tutorApplicationService.hasPendingApplication("user1")).thenReturn(true);

        // Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/start-tutoring/apply")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .param("applicantUsername", "user1")
                        .param("phone", "1234567890")
                        .param("motivation", "I am motivated")
                        .param("subjects", "1")
                        .param("subjects", "2")
                        .param("subjects", "3")
                        .param("experience", "yes"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/start-tutoring/apply"))
                .andExpect(MockMvcResultMatchers.flash().attributeExists("applicationStillPending"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @WithMockUser(username = "user2")
    public void testSubmitApplication_ValidationError() throws Exception {
        // Arrange
        when(tutorApplicationService.hasPendingApplication("user2")).thenReturn(false);

        // Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/start-tutoring/apply")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .param("applicantUsername", "user2")
                        .param("experience", "")
                        .param("motivation", "")
                        .param("phone", "")
                        .param("subjects", ""))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/start-tutoring/apply"))
                .andExpect(MockMvcResultMatchers.flash().attributeExists("applicationData"))
                .andExpect(MockMvcResultMatchers.flash().attributeExists("org.springframework.validation.BindingResult.applicationData"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @WithMockUser(username = "user3")
    public void testSubmitApplication_Success() throws Exception {
        // Arrange
        when(tutorApplicationService.hasPendingApplication("user3")).thenReturn(false);
        when(tutorApplicationService.submitTutorApplication(any(TutorApplicationDTO.class))).thenReturn(true);

        // Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/start-tutoring/apply")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .param("applicantUsername", "user3") // Example parameters
                        .param("experience", "yes")
                        .param("motivation", "I am motivated")
                        .param("phone", "1234567890")
                        .param("subjects", "1,2,3"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/start-tutoring/apply"))
                .andExpect(MockMvcResultMatchers.flash().attributeExists("successfulApplication"))
                .andDo(MockMvcResultHandlers.print());
    }


}
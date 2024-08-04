package bg.softuni.tutorme.web;

import bg.softuni.tutorme.entities.dtos.user.UserRegisterDTO;
import bg.softuni.tutorme.service.UserEntityService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import jakarta.validation.Valid;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class RegisterControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserEntityService mockUserEntityService;
    @Test
    public void testRegisterValidationError() throws Exception {
        UserRegisterDTO invalidData = new UserRegisterDTO();

        when(mockUserEntityService.registerUser(invalidData)).thenReturn(false);

        mockMvc.perform(post("/register")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .param("username", "")
                        .param("fullName", "")
                        .param("email", "notEmail")
                        .param("password", "password")
                        .param("confirmPassword", "password"))
                .andExpect(status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/register"))
                .andExpect(MockMvcResultMatchers.flash().attributeExists("userRegisterObject"))
                .andExpect(MockMvcResultMatchers.flash().attributeExists("org.springframework.validation.BindingResult.userRegisterObject"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testRegisterPasswordMismatch() throws Exception {
        // Arrange
        UserRegisterDTO data = new UserRegisterDTO();
        // Set values where passwords do not match

        // Simulate password mismatch
        when(mockUserEntityService.registerUser(data)).thenReturn(false);

        mockMvc.perform(post("/register")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .param("username", "username")
                        .param("fullName", "really Full name")
                        .param("email", "real@email.com")
                        .param("password", "password123")
                        .param("confirmPassword", "password321")) // Passwords do not match
                .andExpect(status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/register"))
                .andExpect(MockMvcResultMatchers.flash().attributeExists("userRegisterObject"))
                .andExpect(MockMvcResultMatchers.flash().attribute("passwordsDoNotMatch", true))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testRegisterSuccessfulRegistration() throws Exception {
        when(mockUserEntityService.registerUser(any(UserRegisterDTO.class))).thenReturn(true);

        mockMvc.perform(post("/register")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .param("username", "username")
                        .param("fullName", "really Full name")
                        .param("email", "real@email.com")
                        .param("password", "password")
                        .param("confirmPassword", "password"))
                .andExpect(status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/login"))
                .andDo(MockMvcResultHandlers.print());
    }


}
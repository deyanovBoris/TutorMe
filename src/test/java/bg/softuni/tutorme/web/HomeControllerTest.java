package bg.softuni.tutorme.web;

import static org.mockito.Mockito.*;

import java.util.List;

import bg.softuni.tutorme.entities.UserEntity;
import bg.softuni.tutorme.entities.dtos.quotes.QuoteOutputDTO;
import bg.softuni.tutorme.entities.dtos.subjects.SubjectFeatureDTO;
import bg.softuni.tutorme.entities.dtos.tutor.TutorFeatureDTO;
import bg.softuni.tutorme.entities.dtos.user.UserProfileDTO;
import bg.softuni.tutorme.service.QuoteService;
import bg.softuni.tutorme.service.SubjectService;
import bg.softuni.tutorme.service.UserEntityService;
import bg.softuni.tutorme.service.exceptions.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class HomeControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private SubjectService mockSubjectService;
    @MockBean
    private QuoteService mockQuoteService;
    @MockBean
    private UserEntityService mockUserEntityService;
    private HomeController toTest;

    @BeforeEach
    public void setUp() throws UserNotFoundException {
        toTest = new HomeController(mockUserEntityService, mockSubjectService, mockQuoteService);
    }

    @Test
    public void testIndex() throws Exception {
        // Arrange
        List<SubjectFeatureDTO> featuredSubjects = List.of(new SubjectFeatureDTO(), new SubjectFeatureDTO());
        List<TutorFeatureDTO> featuredTutors = List.of(new TutorFeatureDTO(), new TutorFeatureDTO());

        when(mockSubjectService.getFeaturedSubjects()).thenReturn(featuredSubjects);
        when(mockUserEntityService.getFeaturedTutors()).thenReturn(featuredTutors);

        // Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("index"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("homePageStyles"))
                .andExpect(MockMvcResultMatchers.model().attribute("homePageStyles", true))
                .andExpect(MockMvcResultMatchers.model().attribute("featuredSubjects", featuredSubjects))
                .andExpect(MockMvcResultMatchers.model().attribute("featuredTutors", featuredTutors))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @WithMockUser(username = "username")
    public void testDashboard_Success() throws Exception {
        QuoteOutputDTO randomQuote = new QuoteOutputDTO()
                .setQuote("Inspirational quote")
                .setAuthor("Author");
        when(mockQuoteService.getRandomQuote()).thenReturn(randomQuote);

        UserEntity userEntity = new UserEntity()
                .setUsername("username")
                .setEmail("email@example.com");
        UserProfileDTO userProfileDTO = new UserProfileDTO()
                .setUsername(userEntity.getUsername());

        when(mockUserEntityService.getUserByUsername("username"))
                .thenReturn(userProfileDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/user/{username}", "username"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("profile"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("userData"))
                .andExpect(MockMvcResultMatchers.model().attribute("userData", userProfileDTO))
                .andExpect(MockMvcResultMatchers.model().attribute("profilePageStyle", true))
                .andExpect(MockMvcResultMatchers.model().attribute("randomQuote",  randomQuote))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @WithMockUser(username = "notUsername")
    public void testDashboard_UserNotAllowed() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/user/{username}", "username"))
                .andExpect(MockMvcResultMatchers.status().isForbidden())
                .andDo(MockMvcResultHandlers.print());
    }


}



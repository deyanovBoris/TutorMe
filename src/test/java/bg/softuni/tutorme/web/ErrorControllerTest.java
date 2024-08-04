package bg.softuni.tutorme.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
public class ErrorControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetAccessDenied() throws Exception {
        mockMvc.perform(get("/access-denied"))
                .andExpect(status().isOk())
                .andExpect(view().name("/error/403"));
    }
}

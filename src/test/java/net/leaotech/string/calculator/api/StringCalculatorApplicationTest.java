package net.leaotech.string.calculator.api;

import net.leaotech.string.calculator.api.service.CalculationService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * Note that there isn't a separate set of tests for the CalculatorController since it is the only controller
 * and the integration tests are pretty much covering the only endpoint that it contains.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class StringCalculatorApplicationTest {

    private MockMvc mockMvc;

    @Autowired
    private CalculationService calculationService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void prepareMockMvc() {
        this.mockMvc = webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    public void createNewCalculation() throws Exception {
        this.mockMvc.perform(post("/calculator/string").content("2,3").contentType(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk())
                .andExpect(content().string("5"));
    }

    @Test
    public void shouldNotProcessEmptyContent() throws Exception {
        this.mockMvc.perform(post("/calculator/string").contentType(MediaType.TEXT_PLAIN))
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
    }

    @Test
    public void shoulCaptureExceptionMessagesInTheResponse() throws Exception {
        this.mockMvc.perform(post("/calculator/string").content("ab").contentType(MediaType.TEXT_PLAIN))
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()))
                .andExpect(content().string("Invalid input, only numbers allowed"));

        this.mockMvc.perform(post("/calculator/string").content("-3,5").contentType(MediaType.TEXT_PLAIN))
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()))
                .andExpect(content().string("Negative number are not allowed"));

        this.mockMvc.perform(post("/calculator/string").content("-1").contentType(MediaType.TEXT_PLAIN))
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()))
                .andExpect(content().string("Negative number are not allowed"));
    }
}

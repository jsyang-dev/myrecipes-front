package link.myrecipes.front.controller;

import org.junit.Test;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class SystemControllerTest extends ControllerTest {

    @Test
    public void When_컨트롤러_호출_Then_정상_응답() throws Exception {

        // When
        final ResultActions actions = this.mockMvc.perform(get("/health"));

        // Then
        actions.andExpect(status().isOk())
                .andExpect(view().name("health"));
    }
}
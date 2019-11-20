package link.myrecipes.front.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = "spring.config.location="
        + "classpath:/application.yml,"
        + "classpath:/aws.yml"
)
@AutoConfigureMockMvc
public class SystemControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void When_컨트롤러_호출_Then_정상_응답() throws Exception {
        //when
        final ResultActions actions = this.mockMvc.perform(get("/health"));

        //then
        actions.andExpect(status().isOk())
                .andExpect(view().name("health"));
    }
}
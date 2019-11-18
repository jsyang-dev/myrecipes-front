package link.myrecipes.front.controller;

import link.myrecipes.front.dto.User;
import link.myrecipes.front.dto.request.UserRequest;
import link.myrecipes.front.service.MemberServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = "spring.config.location="
        + "classpath:/application.yml,"
        + "classpath:/aws.yml"
)
@AutoConfigureMockMvc
public class MemberControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MemberServiceImpl memberService;

    @Test
    public void Should_정상_리턴_When_회원가입_페이지_호출() throws Exception {
        //when
        final ResultActions actions = this.mockMvc.perform(get("/member/register"));

        //then
        actions.andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("member/register"))
                .andExpect(model().attribute("userRequest", instanceOf(UserRequest.class)))
                .andExpect(content().string(containsString("_csrf")));
    }

    @Test
    public void Should_리다이렉트_When_회원가입_POST_호출() throws Exception {
        //given
        User user = User.builder()
                .id(1)
                .username("user12")
                .password("123456")
                .name("유저12")
                .phone("01012345678")
                .email("user12@domain.com")
                .build();
        given(this.memberService.createMember(user.toRequestDTO())).willReturn(user);

        //when
        final ResultActions actions = this.mockMvc.perform(post("/member/register")
                .param("username", user.getUsername())
                .param("password", user.getPassword())
                .param("name", user.getName())
                .param("phone", user.getPhone())
                .param("email", user.getEmail())
                .with(csrf()));

        //then
        actions.andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }
}
package link.myrecipes.front.controller;

import link.myrecipes.front.dto.User;
import link.myrecipes.front.dto.request.UserRequest;
import link.myrecipes.front.dto.security.UserSecurity;
import link.myrecipes.front.service.MemberServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
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
    private User user;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MemberServiceImpl memberService;

    @Before
    public void setUp() {
        this.user = User.builder()
                .id(1)
                .username("user12")
                .password("123456")
                .name("유저12")
                .phone("01012345678")
                .email("user12@domain.com")
                .build();
    }

    @Test
    public void When_회원가입_페이지_조회_Then_정상_리턴() throws Exception {
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
    public void When_회원가입_저장_Then_리다이렉트() throws Exception {
        //given
        given(this.memberService.createMember(any(UserRequest.class))).willReturn(this.user);

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

    @Test
    public void When_회원가입_파라미터없이_저장_Then_리다이렉트() throws Exception {
        //when
        final ResultActions actions = this.mockMvc.perform(post("/member/register")
                .with(csrf()));

        //then
        actions.andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("member/register"))
                .andExpect(model().hasErrors())
                .andExpect(content().string(containsString("_csrf")))
                .andExpect(content().string(containsString("error-message")));
    }

    @Test
    @WithMockUser
    public void When_회원정보수정_페이지_조회_Then_정상_리턴() throws Exception {
        //given
        given(this.memberService.readMember(any(Integer.class))).willReturn(this.user);

        //when
        final ResultActions actions = this.mockMvc.perform(get("/member/modify"));

        //then
        actions.andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("member/modify"))
                .andExpect(model().attribute("userRequest", instanceOf(UserRequest.class)))
                .andExpect(model().attribute("userId", this.user.getId()))
                .andExpect(content().string(containsString("_csrf")))
                .andExpect(content().string(containsString("value=\"" + user.getUsername() + "\"")))
                .andExpect(content().string(containsString("value=\"" + user.getName() + "\"")))
                .andExpect(content().string(containsString("value=\"" + user.getPhone() + "\"")))
                .andExpect(content().string(containsString("value=\"" + user.getEmail() + "\"")));
    }

    @Test
    @WithUserDetails
    public void When_회원정보수정_저장_Then_리다이렉트() throws Exception {
        //given
        given(this.memberService.updateMember(eq(this.user.getId()), any(UserRequest.class), any(Integer.class))).willReturn(this.user);

        //when
        final ResultActions actions = this.mockMvc.perform(post("/member/modify/" + this.user.getId())
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

    @Test
    @WithMockUser
    public void When_회원정보수정_파라미터없이_저장_Then_리다이렉트() throws Exception {
        //when
        final ResultActions actions = this.mockMvc.perform(post("/member/modify/" + this.user.getId())
                .with(csrf()));

        //then
        actions.andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("member/modify"))
                .andExpect(model().hasErrors())
                .andExpect(content().string(containsString("_csrf")))
                .andExpect(content().string(containsString("error-message")));
    }

    @Configuration
    static class TestBeanConfig {
        @Bean
        public UserDetailsService userDetailsService() {
            return username -> UserSecurity.builder()
                    .id(1)
                    .username("user12")
                    .password("123456")
                    .build();
        }
    }
}
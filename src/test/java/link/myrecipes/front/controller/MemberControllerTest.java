package link.myrecipes.front.controller;

import link.myrecipes.front.dto.User;
import link.myrecipes.front.dto.request.UserRequest;
import link.myrecipes.front.service.MemberServiceImpl;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
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

public class MemberControllerTest extends ControllerTest {

    private User user;

    @MockBean
    private MemberServiceImpl memberService;

    @Before
    public void setUp() {
        this.user = User.builder()
                .id(1)
                .username("test_user")
                .password("123456")
                .name("테스트유저")
                .phone("01012345678")
                .email("test_user@domain.com")
                .build();
    }

    @Test
    public void When_회원가입_페이지_조회_Then_정상_리턴() throws Exception {

        // When
        final ResultActions actions = this.mockMvc.perform(get("/member/register"));

        // Then
        actions.andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("member/register"))
                .andExpect(model().attribute("userRequest", instanceOf(UserRequest.class)))
                .andExpect(content().string(containsString("_csrf")));
    }

    @Test
    public void When_회원가입_저장_Then_리다이렉트() throws Exception {

        // Given
        given(this.memberService.createMember(any(UserRequest.class))).willReturn(this.user);

        // When
        final ResultActions actions = this.mockMvc.perform(post("/member/register")
                .param("username", user.getUsername())
                .param("password", user.getPassword())
                .param("name", user.getName())
                .param("phone", user.getPhone())
                .param("email", user.getEmail())
                .with(csrf()));

        // Then
        actions.andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }

    @Test
    public void When_회원가입_파라미터없이_저장_Then_리다이렉트() throws Exception {

        // When
        final ResultActions actions = this.mockMvc.perform(post("/member/register")
                .with(csrf()));

        // Then
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

        // Given
        given(this.memberService.readMember(any(Integer.class))).willReturn(this.user);

        // When
        final ResultActions actions = this.mockMvc.perform(get("/member/modify"));

        // Then
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
    @WithMockUser
    public void When_회원정보수정_저장_Then_리다이렉트() throws Exception {

        // Given
        given(this.memberService.updateMember(eq(this.user.getId()), any(UserRequest.class), any(Integer.class))).willReturn(this.user);

        // When
        final ResultActions actions = this.mockMvc.perform(post("/member/modify/" + this.user.getId())
                .param("username", user.getUsername())
                .param("password", user.getPassword())
                .param("name", user.getName())
                .param("phone", user.getPhone())
                .param("email", user.getEmail())
                .with(csrf()));

        // Then
        actions.andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }

    @Test
    @WithMockUser
    @Ignore
    public void When_회원정보수정_파라미터없이_저장_Then_리다이렉트() throws Exception {

        // When
        final ResultActions actions = this.mockMvc.perform(post("/member/modify/" + this.user.getId())
                .with(csrf()));

        // Then
        actions.andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("member/modify"))
                .andExpect(model().hasErrors())
                .andExpect(content().string(containsString("_csrf")))
                .andExpect(content().string(containsString("error-message")));
    }
}
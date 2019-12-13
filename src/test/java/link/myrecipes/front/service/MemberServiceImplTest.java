package link.myrecipes.front.service;

import link.myrecipes.front.common.RestTemplateHelperImpl;
import link.myrecipes.front.dto.User;
import link.myrecipes.front.dto.request.UserRequest;
import link.myrecipes.front.dto.security.UserSecurity;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class MemberServiceImplTest {
    private User user;

    @InjectMocks
    private MemberServiceImpl memberService;

    @InjectMocks
    private LoginServiceImpl loginService;

    @Mock
    private RestTemplateHelperImpl restTemplateHelper;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

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
    public void When_로그인_정보_요청_Then_정상_반환() {
        //given
        UserSecurity userSecurity = UserSecurity.builder()
                .id(1)
                .username("test_user")
                .password("123456")
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .enabled(true)
                .build();
        given(this.restTemplateHelper.getForEntity(eq(UserSecurity.class), contains("/login"))).willReturn(userSecurity);

        //when
        final UserSecurity selectedUserSecurity = this.loginService.login(userSecurity.getUsername());

        //then
        assertThat(selectedUserSecurity, instanceOf(UserSecurity.class));
        assertThat(selectedUserSecurity.getId(), is(userSecurity.getId()));
        assertThat(selectedUserSecurity.getUsername(), is(userSecurity.getUsername()));
        assertThat(selectedUserSecurity.getPassword(), is(userSecurity.getPassword()));
        assertThat(selectedUserSecurity.isAccountNonExpired(), is(userSecurity.isAccountNonExpired()));
        assertThat(selectedUserSecurity.isAccountNonLocked(), is(userSecurity.isAccountNonLocked()));
        assertThat(selectedUserSecurity.getId(), is(userSecurity.getId()));
        assertThat(selectedUserSecurity.isCredentialsNonExpired(), is(userSecurity.isCredentialsNonExpired()));
        assertThat(selectedUserSecurity.isEnabled(), is(userSecurity.isEnabled()));
    }

    @Test
    public void When_회원정보_조회_Then_정상_반환() {
        //given
        given(this.restTemplateHelper.getForEntity(eq(User.class), contains("/members"))).willReturn(this.user);

        //when
        final User selectedUser = this.memberService.readMember(1);

        //then
        assertThat(selectedUser, instanceOf(User.class));
        assertThat(selectedUser.getId(), is(this.user.getId()));
        assertThat(selectedUser.getUsername(), is(this.user.getUsername()));
        assertThat(selectedUser.getPassword(), is(this.user.getPassword()));
        assertThat(selectedUser.getName(), is(this.user.getName()));
        assertThat(selectedUser.getPhone(), is(this.user.getPhone()));
        assertThat(selectedUser.getEmail(), is(this.user.getEmail()));
    }

    @Test
    public void When_회원정보_저장_Then_정상_반환() {
        //given
        given(this.restTemplateHelper.postForEntity(eq(User.class), contains("/members"), any(UserRequest.class))).willReturn(this.user);

        //when
        final User selectedUser = this.memberService.createMember(user.toRequestDTO());

        //then
        assertThat(selectedUser, instanceOf(User.class));
        assertThat(selectedUser.getId(), is(this.user.getId()));
        assertThat(selectedUser.getUsername(), is(this.user.getUsername()));
        assertThat(selectedUser.getPassword(), is(this.user.getPassword()));
        assertThat(selectedUser.getName(), is(this.user.getName()));
        assertThat(selectedUser.getPhone(), is(this.user.getPhone()));
        assertThat(selectedUser.getEmail(), is(this.user.getEmail()));
    }

    @Test
    public void When_회원정보_수정_Then_정상_반환() {
        //given
        given(this.restTemplateHelper.putForEntity(eq(User.class), contains("/members"), any(UserRequest.class))).willReturn(this.user);

        //when
        final User selectedUser = this.memberService.updateMember(this.user.getId(), user.toRequestDTO(), 1);

        //then
        assertThat(selectedUser, instanceOf(User.class));
        assertThat(selectedUser.getId(), is(this.user.getId()));
        assertThat(selectedUser.getUsername(), is(this.user.getUsername()));
        assertThat(selectedUser.getPassword(), is(this.user.getPassword()));
        assertThat(selectedUser.getName(), is(this.user.getName()));
        assertThat(selectedUser.getPhone(), is(this.user.getPhone()));
        assertThat(selectedUser.getEmail(), is(this.user.getEmail()));
    }
}
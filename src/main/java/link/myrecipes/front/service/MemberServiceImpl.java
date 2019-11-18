package link.myrecipes.front.service;

import link.myrecipes.front.common.RestTemplateHelperImpl;
import link.myrecipes.front.dto.User;
import link.myrecipes.front.dto.request.UserRequest;
import link.myrecipes.front.dto.security.UserSecurity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class MemberServiceImpl implements MemberService {
    @Value("${app.api.member.scheme}")
    private String scheme;

    @Value("${app.api.member.host}")
    private String host;

    @Value("${app.api.member.port}")
    private String port;

    private final RestTemplateHelperImpl restTemplateHelper;
    private final PasswordEncoder passwordEncoder;

    public MemberServiceImpl(RestTemplateHelperImpl restTemplateHelper, PasswordEncoder passwordEncoder) {
        this.restTemplateHelper = restTemplateHelper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserSecurity login(String username) {
        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .scheme(this.scheme)
                .host(this.host)
                .port(this.port)
                .path("/login")
                .path("/" + username)
                .build(true);

        return this.restTemplateHelper.getForEntity(UserSecurity.class, uriComponents.toUriString());
    }

    @Override
    public User readMember() {
        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .scheme(this.scheme)
                .host(this.host)
                .port(this.port)
                .path("/members")
                .path("/" + getLoginUserId())
                .build(true);

        return this.restTemplateHelper.getForEntity(User.class, uriComponents.toUriString());
    }

    @Override
    public User createMember(UserRequest userRequest) {
        userRequest.encodePassword(passwordEncoder);

        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .scheme(this.scheme)
                .host(this.host)
                .port(this.port)
                .path("/members")
                .build(true);

        return this.restTemplateHelper.postForEntity(User.class, uriComponents.toUriString(), userRequest);
    }

    @Override
    public User updateMember(int id, UserRequest userRequest) {
        userRequest.encodePassword(passwordEncoder);

        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .scheme(this.scheme)
                .host(this.host)
                .port(this.port)
                .path("/members")
                .path("/" + id)
                .queryParam("userId", getLoginUserId())
                .build(true);

        return this.restTemplateHelper.putForEntity(User.class, uriComponents.toUriString(), userRequest);
    }

    private int getLoginUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserSecurity userSecurity = (UserSecurity) authentication.getPrincipal();
        return userSecurity.getId();
    }
}

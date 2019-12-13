package link.myrecipes.front.service;

import link.myrecipes.front.common.RestTemplateHelper;
import link.myrecipes.front.dto.User;
import link.myrecipes.front.dto.request.UserRequest;
import org.springframework.beans.factory.annotation.Value;
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

    private final RestTemplateHelper restTemplateHelper;
    private final PasswordEncoder passwordEncoder;

    public MemberServiceImpl(RestTemplateHelper restTemplateHelper, PasswordEncoder passwordEncoder) {
        this.restTemplateHelper = restTemplateHelper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User readMember(int loginUserId) {
        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .scheme(this.scheme)
                .host(this.host)
                .port(this.port)
                .path("/members")
                .path("/" + loginUserId)
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
    public User updateMember(int id, UserRequest userRequest, int loginUserId) {
        userRequest.encodePassword(passwordEncoder);

        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .scheme(this.scheme)
                .host(this.host)
                .port(this.port)
                .path("/members")
                .path("/" + id)
                .queryParam("userId", loginUserId)
                .build(true);

        return this.restTemplateHelper.putForEntity(User.class, uriComponents.toUriString(), userRequest);
    }
}

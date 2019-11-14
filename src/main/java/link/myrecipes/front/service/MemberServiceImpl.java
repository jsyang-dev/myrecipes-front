package link.myrecipes.front.service;

import link.myrecipes.front.common.RestTemplateHelperImpl;
import link.myrecipes.front.dto.security.UserSecurity;
import org.springframework.beans.factory.annotation.Value;
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

    public MemberServiceImpl(RestTemplateHelperImpl restTemplateHelper) {
        this.restTemplateHelper = restTemplateHelper;
    }

    @Override
    public UserSecurity readUserSecurity(String username) {
        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .scheme(this.scheme)
                .host(this.host)
                .port(this.port)
                .path("/users")
                .path("/" + username)
                .build(true);

        return this.restTemplateHelper.getForEntity(UserSecurity.class, uriComponents.toUriString());
    }
}

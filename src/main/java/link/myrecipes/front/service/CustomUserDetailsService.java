package link.myrecipes.front.service;

import link.myrecipes.front.common.RestTemplateHelper;
import link.myrecipes.front.dto.security.UserSecurity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Value("${app.api.member.scheme}")
    private String scheme;

    @Value("${app.api.member.host}")
    private String host;

    @Value("${app.api.member.port}")
    private String port;

    private final RestTemplateHelper restTemplateHelper;

    public CustomUserDetailsService(RestTemplateHelper restTemplateHelper) {
        this.restTemplateHelper = restTemplateHelper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserSecurity userSecurity = login(username);

        if (userSecurity == null) {
            throw new UsernameNotFoundException(username);
        }

        return userSecurity;
    }

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
}

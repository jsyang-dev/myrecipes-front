package link.myrecipes.front.service;

import link.myrecipes.front.dto.security.UserSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final MemberService memberService;

    public CustomUserDetailsService(MemberService memberService) {
        this.memberService = memberService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserSecurity userSecurity = this.memberService.login(username);

        if (userSecurity == null) {
            throw new UsernameNotFoundException(username);
        }

        return userSecurity;
    }
}

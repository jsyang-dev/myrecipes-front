package link.myrecipes.front.common;

import link.myrecipes.front.dto.security.UserSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SecurityHelperImpl implements SecurityHelper {
    public int getLoginUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserSecurity userSecurity = (UserSecurity) authentication.getPrincipal();
        return userSecurity.getId();
    }
}

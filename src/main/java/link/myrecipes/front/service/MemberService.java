package link.myrecipes.front.service;

import link.myrecipes.front.dto.security.UserSecurity;

public interface MemberService {
    UserSecurity readUserSecurity(String username);
}

package link.myrecipes.front.service;

import link.myrecipes.front.dto.User;
import link.myrecipes.front.dto.request.UserRequest;
import link.myrecipes.front.dto.security.UserSecurity;

public interface MemberService {
    UserSecurity login(String username);

    User createMember(UserRequest userRequest);
}

package link.myrecipes.front.service;

import link.myrecipes.front.dto.security.UserSecurity;

public interface LoginService {
    UserSecurity login(String username);
}

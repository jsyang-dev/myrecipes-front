package link.myrecipes.front.service;

import link.myrecipes.front.dto.User;
import link.myrecipes.front.dto.request.UserRequest;

public interface MemberService {
    User readMember(int loginUserId);

    User createMember(UserRequest userRequest);

    User updateMember(int id, UserRequest userRequest, int loginUserId);
}

package link.myrecipes.front.dto.security;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserRoleSecurity {
    private String role;

    String getRole() {
        return role;
    }

    @Builder
    public UserRoleSecurity(String role) {
        this.role = role;
    }
}

package link.myrecipes.front.dto.security;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class UserRoleSecurity implements Serializable {
    private String role;

    String getRole() {
        return role;
    }

    public UserRoleSecurity(String role) {
        this.role = role;
    }
}

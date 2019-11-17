package link.myrecipes.front.dto.security;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserAuthoritySecurity {
    private String authority;

    String getAuthority() {
        return authority;
    }

    @Builder
    public UserAuthoritySecurity(String authority) {
        this.authority = authority;
    }
}

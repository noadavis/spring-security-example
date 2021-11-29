package springboot.v2.security.example.user;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    USER,
    EDITOR,
    ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }

    public String toString() {
        return name();
    }
}

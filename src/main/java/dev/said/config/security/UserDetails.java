package dev.said.config.security;

import dev.said.domains.AuthUser;
import dev.said.enums.Active;
import lombok.Getter;
import org.springframework.lang.NonNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Collections;

@Getter
public class UserDetails implements org.springframework.security.core.userdetails.UserDetails {

    private final AuthUser authUser;
    private final Long id;

    public UserDetails(@NonNull AuthUser authUser) {
        this.authUser = authUser;
        this.id = authUser.getId();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        System.out.println("===========================================================");
        System.out.println("authUser.getRole() = " + authUser.getRole());
        System.out.println("===========================================================");
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");


        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + authUser.getRole()));
//        return Collections.singletonList(new SimpleGrantedAuthority(String.valueOf(authUser.getRole())));
    }

    @Override
    public String getPassword() {
        return authUser.getPassword();
    }

    @Override
    public String getUsername() {
        return authUser.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return authUser.getActive().equals(Active.ACTIVE);
    }

    public Long getId() {
        return id;
    }

    public AuthUser getAuthUser() {
        return authUser;
    }
}

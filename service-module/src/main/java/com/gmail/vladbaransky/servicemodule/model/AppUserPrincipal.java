package com.gmail.vladbaransky.servicemodule.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class AppUserPrincipal implements UserDetails {
    private static final String ROLE_PREFIX = "ROLE_";
    private UserDTO user;
    private Set<GrantedAuthority> grantedAuthorities = new HashSet<>();

    public AppUserPrincipal(UserDTO user) {
        this.user = user;
        this.grantedAuthorities.add(new SimpleGrantedAuthority(ROLE_PREFIX + user.getRole().name()));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
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
        return true;
    }

}

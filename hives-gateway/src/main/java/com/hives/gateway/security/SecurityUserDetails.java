package com.hives.gateway.security;

import com.hives.common.to.UserTo;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;

public class SecurityUserDetails extends User implements Serializable {
 
    private UserTo user;

    public SecurityUserDetails(UserTo userTo, Collection<? extends GrantedAuthority> authorities, Long userId) {
        super(userTo.getEmail(), userTo.getPassword(), authorities);
        this.user = userTo;
    }

    public SecurityUserDetails(UserTo userTo, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities, Long userId) {
        super(userTo.getEmail(), userTo.getPassword(), enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.user = userTo;
    }

    public UserTo getUser(){
        return user;
    }

    public Long getUserId() {
        return user.getId();
    }

    public void setUserId(Long userId) {
        this.user.setId(userId);
    }
}
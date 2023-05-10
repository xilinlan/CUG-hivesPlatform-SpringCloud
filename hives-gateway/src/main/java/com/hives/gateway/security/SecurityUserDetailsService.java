package com.hives.gateway.security;

import com.hives.common.to.UserTo;
import com.hives.gateway.feign.UserFeignService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: zhangtao
 * @Date: 2023/04/27/12:57
 * @Description:
 */

@Service
public class SecurityUserDetailsService implements ReactiveUserDetailsService {

    // private final PasswordEncoder passwordEncoder= new BCryptPasswordEncoder();

    @Autowired
    private UserFeignService userFeignService;

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        UserTo user=userFeignService.UserByEmail(username);
        user.setPassword("{bcrypt}"+ user.getPassword());
        //调用数据库根据用户名获取用户

        Collection<GrantedAuthority> authorities = new ArrayList<>();
        if(user!=null){
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        }
        SecurityUserDetails securityUserDetails = new SecurityUserDetails(user,authorities,1L);
        return Mono.just(securityUserDetails);
    }
}

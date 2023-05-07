package com.hives.gateway.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class AuthorizationManager implements ReactiveAuthorizationManager<AuthorizationContext> {
    @Override
    public Mono<AuthorizationDecision> check(Mono<Authentication> authentication, AuthorizationContext authorizationContext) {
        return authentication.map(auth -> {
            //SecurityUserDetails userSecurity = (SecurityUserDetails) auth.getPrincipal();
            String path=authorizationContext.getExchange().getRequest().getURI().getPath();
            for (GrantedAuthority authority : auth.getAuthorities()){
                if ("ROLE_USER".equals(authority.getAuthority())|| "ROLE_ADMIN".equals(authority.getAuthority())) {
                    return new AuthorizationDecision(true);
                }
//对客户端访问路径与用户角色进行匹配
            }
            return new AuthorizationDecision(false);
        }).defaultIfEmpty(new AuthorizationDecision(false));
    }

    @Override
    public Mono<Void> verify(Mono<Authentication> authentication, AuthorizationContext object) {
        return ReactiveAuthorizationManager.super.verify(authentication, object);
    }
}
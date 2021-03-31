package com.repetentia.component.security.jwt;

import java.util.Collection;

import org.slf4j.Logger;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import com.repetentia.component.log.RtaLogFactory;

public class RtaJwtAuthenticationToken extends AbstractAuthenticationToken {
    private static final Logger log = RtaLogFactory.getLogger(RtaJwtAuthenticationToken.class);

//    public RtaJwtAuthenticationToken(Jwt<Claims> jwt, Collection<? extends GrantedAuthority> authorities) {
//        super(authorities);
//
//    }
    public RtaJwtAuthenticationToken(Collection<? extends GrantedAuthority> authorities) {

        super(authorities);
    }

    @Override
    public Object getCredentials() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Object getPrincipal() {
        // TODO Auto-generated method stub
        return null;
    }

}

package com.repetentia.component.security;

import org.slf4j.Logger;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.repetentia.component.log.RtaLogFactory;

public class RtaAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {
    private static final Logger log = RtaLogFactory.getLogger(RtaAuthenticationProvider.class);

    private UserDetailsService userDetailsService;

    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    // DaoAuthenticationProvider 참조
    // UsernamePasswordAuthenticationFilter
    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        log.info("# CUSTOM AUTH - additionalAuthenticationChecks {}", userDetails);
    }

    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication)
            throws AuthenticationException {
        log.info("# CUSTOM AUTH - {}", username);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        log.info("# USER DETAILS - {}", userDetails);
        return userDetails;
    }

}

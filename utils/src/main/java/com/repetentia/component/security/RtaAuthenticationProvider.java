package com.repetentia.component.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RtaAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {
    // DaoAuthenticationProvider 참조
    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails,
            UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {

    }

    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication)
            throws AuthenticationException {

        return null;
    }


}

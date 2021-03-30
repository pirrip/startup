package com.repetentia.component.security.jwt;

import java.io.IOException;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RtaJwtAuthenticationFilter extends GenericFilterBean {
    private AuthenticationManager authenticationManager;
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String bearer = httpServletRequest.getHeader("Authorization");
        if (StringUtils.hasText(bearer) && bearer.startsWith("Bearer")) {
            String token = bearer.substring(7);
            log.info("TOKEN - {}", token);
            io.jsonwebtoken.Jwt<Header, Claims> jwt =Jwts.parser().setSigningKey("youdon'tknowthesecret").parse(token);
            Header header = jwt.getHeader();
            Claims claims = jwt.getBody();
            Date issuedAt = claims.getIssuedAt();
            Date expiresAt = claims.getExpiration();
    //
            Jwt jwtToken = new Jwt(token, issuedAt.toInstant(), expiresAt.toInstant(), header, claims);
            JwtAuthenticationToken authenticationToken = new JwtAuthenticationToken(jwtToken);
            JwtAuthenticationToken authenticatedToken = (JwtAuthenticationToken)authenticationManager.authenticate(authenticationToken);
            SecurityContext context = SecurityContextHolder.getContext();
            context.setAuthentication(authenticatedToken);
            log.info("AUTH - {}", bearer);

            chain.doFilter(request, response);
            SecurityContextHolder.clearContext();

        } else {
            chain.doFilter(request, response);
        }
    }
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }
}

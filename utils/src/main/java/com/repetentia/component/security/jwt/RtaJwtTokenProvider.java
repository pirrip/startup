package com.repetentia.component.security.jwt;

import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;

import com.repetentia.component.log.RtaLogFactory;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

public class RtaJwtTokenProvider {
    private static final Logger log = RtaLogFactory.getLogger(RtaJwtTokenProvider.class);

    private final long TOKEN_VALID_MILISECOND = 1000L * 60 * 60 * 10;
    private byte[] pwd = "jE3MTU2NDE5LCJpYXQiOjE2MTcxOTI08jE3MTU2NDE5LCJpYXQiOjE2MTcxOTI08".getBytes();

    public void setKey(String pwd) {
        this.pwd = pwd.getBytes();
    }

    public String issue() {
        Key key = Keys.hmacShaKeyFor(pwd);
        Map<String, Object> header = new HashMap<String, Object>();
        Instant now = Instant.now();
        String jws = Jwts.builder()
                .setHeader(header)
                .setSubject("user")
                .setExpiration(Date.from(now.plusMillis(TOKEN_VALID_MILISECOND)))
                .setIssuedAt(Date.from(now))
                .signWith(key)
                .compact();
        return jws;
    }

    public Jws<Claims> parse(String token) {
        Key key = Keys.hmacShaKeyFor(pwd);
        Jws<Claims> jws = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);
        return jws;
    }

    public static void main(String[] args) {
        RtaJwtTokenProvider rtaJwtTokenProvider = new RtaJwtTokenProvider();
        String jws = rtaJwtTokenProvider.issue();
        Jws<Claims> jwsClaims = rtaJwtTokenProvider.parse(jws);
        System.out.println(jws);
        System.out.println(jwsClaims);
    }

}
package com.repetentia.component.security.jwt;

import java.security.Key;

import org.slf4j.Logger;

import com.repetentia.component.log.RtaLogFactory;

import io.jsonwebtoken.security.Keys;

public class RtaJwtKeyProvider {
    private static final Logger log = RtaLogFactory.getLogger(RtaJwtKeyProvider.class);
    public Key getKey() {
        byte [] pwd = "".getBytes();
        Key key = Keys.hmacShaKeyFor(pwd);
        return key;
    }

}
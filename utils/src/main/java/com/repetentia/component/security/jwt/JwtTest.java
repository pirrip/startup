package com.repetentia.component.security.jwt;

import org.springframework.security.oauth2.jwt.Jwt;

public class JwtTest {
    public static void main(String[] args) {
        String token = "{\"user\":\"pirrip\"}";
        Jwt.Builder builder = Jwt.withTokenValue(token);
        builder.header("set", "as");
        builder.claim("id", "myryne");
        Jwt jwt = builder.build();

        System.out.println(jwt.toString());

    }
}

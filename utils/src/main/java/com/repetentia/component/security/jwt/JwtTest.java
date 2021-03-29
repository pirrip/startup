package com.repetentia.component.security.jwt;

import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SigningKeyResolver;
import io.jsonwebtoken.SigningKeyResolverAdapter;

public class JwtTest {
    public static String createToken() {
        Claims claims = Jwts.claims().setSubject("pirrip@naver.com");
        SimpleGrantedAuthority ga1 = new SimpleGrantedAuthority("jwt");
        SimpleGrantedAuthority ga2 = new SimpleGrantedAuthority("test");
        List<GrantedAuthority> roles = new ArrayList<GrantedAuthority>();
        roles.add(ga1);
        roles.add(ga2);
        claims.put("roles", roles);

        Date now = new Date();
        String tkn = Jwts.builder()
                .setClaims(claims) // 데이터
                .setIssuedAt(now)   // 토큰 발행 일자
                .setExpiration(new Date(now.getTime() + 24*60*60*1000)) // 만료 기간
                .signWith(SignatureAlgorithm.HS512, "youdon'tknowthesecret") // 암호화 알고리즘, secret 값
                .compact(); // Token 생성
        return tkn;
    }
    public static void main(String[] args) throws UnsupportedEncodingException {
        System.out.println(createToken());
        String tkn = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJwaXJyaXBAbmF2ZXIuY29tIiwicm9sZXMiOlt7ImF1dGhvcml0eSI6Imp3dCJ9LHsiYXV0aG9yaXR5IjoidGVzdCJ9XSwiaWF0IjoxNjE3MDA0Mzc0LCJleHAiOjE2MTcwOTA3NzR9.DjnmPL2Zednky_Z4QflehwamMsQCbA4exQzF02KF4mdJB3smfGLAplxWYVsprGLLhkqo4q-maRUTCnACDOCA1Q";
//        Jws<Claims> claims = Jwts.parser().setSigningKey("secret").parseClaimsJws(tkn);
//        System.out.println(claims);

//        String s = Jwts.builder()
//                .setSubject("1234567890")
//                .setId("19aa67ad-4fa9-42e0-bcc5-7dd41189bdc1")
//                .setIssuedAt(Date.from(Instant.ofEpochSecond(1617005539)))
//                .setExpiration(Date.from(Instant.ofEpochSecond(1617009139)))
//                .claim("name", "John Doe")
//                .claim("admin", true)
//                .signWith(SignatureAlgorithm.HS256, "secret".getBytes("UTF-8"))
//                .compact();
//
//        io.jsonwebtoken.Jwt jwt = Jwts.parser().parse(tkn);
//        System.out.println(jwt);

        SigningKeyResolver signingKeyResolver = new SigningKeyResolverAdapter() {
            @Override
            public Key resolveSigningKey(JwsHeader header, Claims claims) {
                    System.out.println(header);
                    System.out.println(claims);
                // Examine header and claims
                return null; // will throw exception, can be caught in caller
            }
        };

        try {
            Claims c = Jwts.parser()
                .setSigningKeyResolver(signingKeyResolver)
                .parseClaimsJws(tkn)
                .getBody();
            System.out.println(c);
        } catch (Exception e) {
            e.printStackTrace();
          // no signing key on client. We trust that this JWT came from the server and has been verified there
        }
    }

}

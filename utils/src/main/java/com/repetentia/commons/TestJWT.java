//package com.repetentia.commons;
//
//import java.io.UnsupportedEncodingException;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.ExpiredJwtException;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//
//public class TestJWT {
//    final String key = "Bamdule";
//
//    public static void main(String[] args) throws UnsupportedEncodingException {
//        TestJWT testJWT = new TestJWT();
//
//        String jwt = testJWT.createToken();
//        System.out.println(jwt);
//
//        Map<String, Object> claimMap = testJWT.verifyJWT(jwt);
//        System.out.println(claimMap); // 토큰이 만료되었거나 문제가있으면 null
//    }
//
//    // 토큰 생성
//    public String createToken() {
//
//        // Header 부분 설정
//        Map<String, Object> headers = new HashMap<>();
//        headers.put("typ", "JWT");
//        headers.put("alg", "HS256");
//
//        // payload 부분 설정
//        Map<String, Object> payloads = new HashMap<>();
//        payloads.put("data", "My First JWT !!");
//
//        Long expiredTime = 1000 * 60L * 60L * 2L; // 토큰 유효 시간 (2시간)
//
//        Date ext = new Date(); // 토큰 만료 시간
//        ext.setTime(ext.getTime() + expiredTime);
//
//        // 토큰 Builder
//        String jwt = Jwts.builder().setHeader(headers) // Headers 설정
//                .setClaims(payloads) // Claims 설정
//                .setSubject("user") // 토큰 용도
//                .setExpiration(ext) // 토큰 만료 시간 설정
//                .signWith(SignatureAlgorithm.HS256, key.getBytes()) // HS256과 Key로 Sign
//                .compact(); // 토큰 생성
//
//        return jwt;
//    }
//
//    // 토큰 검증
//    public Map<String, Object> verifyJWT(String jwt) throws UnsupportedEncodingException {
//        Map<String, Object> claimMap = null;
//        try {
//            Claims claims = Jwts.parser()
//                    .setSigningKey(key.getBytes("UTF-8")) // Set Key
//                    .parseClaimsJws(jwt) // 파싱 및 검증, 실패 시 에러
//                    .getBody();
//
//            claimMap = claims;
//
//            //Date expiration = claims.get("exp", Date.class);
//            //String data = claims.get("data", String.class);
//            
//        } catch (ExpiredJwtException e) { // 토큰이 만료되었을 경우
//            System.out.println(e);
//        } catch (Exception e) { // 그외 에러났을 경우
//            System.out.println(e);
//        }
//        return claimMap;
//    }
//}

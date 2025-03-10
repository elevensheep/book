package com.book.book.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {

    static final SecretKey key =
            Keys.hmacShaKeyFor(Decoders.BASE64.decode(
                    "jwtpassword123jwtpassword123jwtpassword123jwtpassword123jwtpassword"));

    // JWT 만들어주는 함수
    public static String createToken(Authentication auth){
        var user = (User) auth.getPrincipal();
        // .claim(이름, 값)으로 JWT에 데이터 추가 가능
        String jwt = Jwts.builder()
                .claim("username", "")
                .claim("userNickname", "")
                .issuedAt(new Date(System.currentTimeMillis()))  // 발행 일자
                .expiration(new Date(System.currentTimeMillis() + 100000))  // 유효기간 1000초
                .signWith(key)
                .compact();

        return jwt;
    }

    // JWT 까주는 함수
    public static Claims extractToken(String token){
        Claims claims = Jwts.parser().verifyWith(key).build()
                .parseClaimsJws(token).getPayload();
        return claims;
    }
}

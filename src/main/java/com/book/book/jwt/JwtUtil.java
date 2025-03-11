package com.book.book.jwt;

import com.book.book.dto.CustomUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JwtUtil {

    static final SecretKey key =
            Keys.hmacShaKeyFor(Decoders.BASE64.decode(
                    "jwtpassword123jwtpassword123jwtpassword123jwtpassword123jwtpassword"));

    // JWT 만들어주는 함수
    public static String createToken(Authentication auth){
        CustomUser customUser = (CustomUser) auth.getPrincipal();

        var authorities = auth.getAuthorities().stream().map(a -> a.getAuthority())
                .collect(Collectors.joining(","));


        // .claim(이름, 값)으로 JWT에 데이터 추가 가능
        String jwt = Jwts.builder()
                .claim("userUuid", customUser.getUserUuid())
                .claim("userNickname", customUser.getUserNickname())
                .claim("authorities", authorities)
                .issuedAt(new Date(System.currentTimeMillis()))  // 발행 일자
                .expiration(new Date(System.currentTimeMillis() + 3600000))  // 유효기간 1시간(3600초)
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

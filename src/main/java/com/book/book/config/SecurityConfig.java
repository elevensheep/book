package com.book.book.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable()); //  프론트엔드와 백엔드가 다른 포트를 사용할 때 CORS와 관련된 문제를 피하기 위해 CSRF 보호를 비활성화

        // SessionCreationPolicy.STATELESS : 로그인 시 세션데이터 만들지 마 (JWT 인증을 사용하는 경우, 세션을 서버에서 관리할 필요 없음)
        http.sessionManagement((session) -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login", "/signup", "/swagger-ui.html").permitAll() // 로그인하지 않은 사용자도 접근 가능
                        .anyRequest().authenticated() // 나머지는 인증 필요
                );

//        http.formLogin(form -> form
//                        .loginProcessingUrl("/login") // Spring Security가 로그인 처리
//                        .usernameParameter("username") // 프론트엔드에서 보낸 username을 매핑
//                        .passwordParameter("userPassword") // 프론트엔드에서 보낸 userPassword를 매핑
//                        .defaultSuccessUrl("/", true) // 로그인 성공 시 이동할 경로
//                        .failureUrl("/login?error=true") // 로그인 실패 시 이동할 경로
//                        .permitAll()
//                );

        http.logout(logout -> logout
                        .logoutUrl("/logout") // 로그아웃 처리
                        .logoutSuccessUrl("/") // 로그아웃 성공 후 이동할 경로
                        .permitAll()
                );

        http.cors(cors -> cors.configurationSource(corsConfigurationSource()));  // CORS 설정 추가

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("http://localhost:3000");  // 허용할 도메인
        configuration.addAllowedMethod("*");  // 허용할 HTTP 메소드 (GET, POST 등)
        configuration.addAllowedHeader("*");  // 허용할 HTTP 헤더
        configuration.setAllowCredentials(true);  // 쿠키 전송 허용

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);  // 모든 경로에 대해 CORS 설정 적용

        return source;
    }
}

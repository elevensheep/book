package com.book.book.controller;

import com.book.book.entity.TbUser;
import com.book.book.repository.TbUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class TbUserController {
    private final TbUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    // JWT 로그인
    @ResponseBody
    @PostMapping("/login")
    public String login(@RequestBody Map<String, String> data) {
        var authehToken = new UsernamePasswordAuthenticationToken(
                data.get("username"), data.get("userPassword"));
                    // 아이디/비번을 DB와 비교해서 로그인 해줌
        var auth = authenticationManagerBuilder.getObject().authenticate(authehToken);

        SecurityContextHolder.getContext().setAuthentication(auth);

        return "";
    }

    // 세션 로그인
//    @PostMapping("/login")
//    public String login(@RequestBody TbUser tbUser) {
//        Optional<TbUser> result = userRepository.findByUserName("jw");
//
//        if (result.isPresent()) {
//            System.out.println(result.get());
//        } else {
//            System.out.println("사용자를 찾을 수 없습니다.");
//        }
//
//        return "";
//    }

    @PostMapping("/signup")
    public String signup(@RequestBody TbUser tbUser) {
        TbUser User = new TbUser();
        var hashPassword = passwordEncoder.encode((tbUser.getUserPassword()));

        tbUser.setUsername(tbUser.getUsername());
        tbUser.setUserPassword(hashPassword);

        userRepository.save(tbUser);

        return "";
    }

    @GetMapping("/bookmark")
    public void bookmark(Authentication auth) {
        System.out.println(auth.getPrincipal());
        System.out.println(auth.getName());
    }
    @GetMapping("/")
    public void home(Authentication auth) {
        if (auth != null) {
            System.out.println(auth.getPrincipal());  // 로그인한 경우
            System.out.println(auth.getName());  // 로그인한 경우
        } else {
            System.out.println("로그인되지 않은 사용자입니다.");
        }

    }
}

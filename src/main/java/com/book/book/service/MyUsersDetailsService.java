package com.book.book.service;

import com.book.book.entity.TbUser;
import com.book.book.repository.TbUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

// DB 유저 정보 꺼내기
// DB에서 동일 아이디의 유저 정보 꺼냄 -> 유저가 제출한 비번과 DB 비번 비교해줌 -> 일치하면 쿠키 생성해서 유저한테 보내줌. 세션도 보내줌
// 스프링 시큐리티는 DB 비번 어딨는지 몰라서 찾아서 줘야됨
@Service
@RequiredArgsConstructor
public class MyUsersDetailsService implements UserDetailsService {

    private final TbUserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var result = userRepository.findByUsername(username);
        if(result.isEmpty()){
            throw new UsernameNotFoundException(username + "라는 아이디를 찾을 수 없습니다.");
        }
        var user = result.get();
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("일반회원"));

        return new User(user.getUsername(), user.getUserPassword(), authorities);
    }

}

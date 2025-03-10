package com.book.book.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

@Entity
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_users")
public class TbUser {

    @Id
    @Column(name = "users_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(name ="users_name", unique = true, nullable = false)
    private String username; // 아이디

    @Column(name = "users_nickname", unique = true, nullable = false)
    private String userNickname;  // 별명

    @Column(name ="users_password", nullable = false)
    private String userPassword;

}

package com.book.book.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_newsKeyword")
public class TbNewsKeyword {

    @Id
    @Column(name = "news_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long newsId;

    private LocalDateTime newsDate;  // 업데이트한 시간

    // 카테고리 추가
    private String newsKeyword;
}

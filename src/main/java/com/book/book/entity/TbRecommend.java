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
@Table(name = "tb_recomend")
public class TbRecommend {

    // 뉴스 카테고리별 분류해서 책 저장
    // 한 책이 여러 뉴스 카테고리에 중복될 수 있음
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recommendId;

    @ManyToOne
    @JoinColumn(name = "booksKeyword_id")
    private TbBookKeyword bookKeyword;  // ISBN

    @ManyToOne
    @JoinColumn(name = "news_id")
    private TbNewsKeyword newsKeyword;

}

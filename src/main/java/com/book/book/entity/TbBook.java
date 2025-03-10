package com.book.book.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_books")
public class TbBook {
    @Id
    @Column(name = "books_isbn")
    private String bookIsbn; // ISBN

    @Column(name="books_title")
    private String bookTitle;

    @Column(name = "books_publisher")
    private String bookPublisher;

    @Column(name = "books_author")
    private String bookAuthor;

    @Column(name = "books_img")
    private String bookImg;


    @Column(name = "books_description")
    @Lob // 대용량 텍스트 데이터임을 명시
    private String bookDescription;

    @Column(name = "books_category")
    private String bookCategory;

}

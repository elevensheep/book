package com.book.book.repository;

import com.book.book.entity.TbBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository

public interface TbBookRepository extends JpaRepository<TbBook, Integer> {

    List<TbBook> findAllByBookCategory(String category);

    TbBook findByBookIsbn(String isbn);

    // 책 제목에 검색어가 포함된 도서 찾기
    List<TbBook> findByBookTitleContainingIgnoreCase(String search);
}

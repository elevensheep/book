package com.book.book.controller;
// 책 추천 및 조회

import com.book.book.dto.TbBookDto;
import com.book.book.entity.TbRecommend;
import com.book.book.service.TbRecommendService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

/**
 RecommendController (/api/recommendations)

 GET

 /api/recommendations/keyword/{keyword}

 Path Variable

 뉴스 키워드 기반 도서 추천


 GET

 /api/recommendations/category/{category}

 Path Variable

 뉴스 카테고리별 도서 추천


 GET

 /api/recommendations/date/{date}

 Path Variable

 특정 날짜의 뉴스 키워드 기반 도서 추천

 * */

@RestController
@RequestMapping("/recommendations")
@RequiredArgsConstructor
public class TbRecommendController {

    private final TbRecommendService tbRecommendService;

    @GetMapping("/books")
    public ResponseEntity<List<TbBookDto>> getRecommendedBooks(@RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd")LocalDate date) {
        List<TbBookDto> recommendedBooks = tbRecommendService.getRecommendedBooksByDate(date);
        return ResponseEntity.ok(recommendedBooks);
    }
//    @Autowired
//    private TbRecommendService recommendService;

//    @GetMapping
//    public List<Recommend> recommend() {
//
//    }

}

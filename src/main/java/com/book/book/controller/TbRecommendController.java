package com.book.book.controller;
// 책 추천 및 조회

import com.book.book.service.TbRecommendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 검색어(키워드)에 맞는 책 추천 -> /api/search?query=검색어
 * 뉴스 카테고리별 책 추천 -> /api/recommendations/{category}
 * 경제 -> api/recommendations/news/economy
 * 정치 -> api/recommendations/news/politics
 * 사회 -> api/recommendations/news/society
 * 스포츠 -> api/recommendations/news/sports
 * */


/**
 * recommend 테이블 :  "현재 추천되는 책 리스트"를 저장하는 용도
 *
 *
 * 📌 컨트롤러 역할 정리
 * ✅ BookController (/api/books) 책 정보 기반 추천
 * GET /api/books?search= -> 도서 검색(제목 or 키워드), 검색창 사용
 * GET /api/books?category -> 도서 카테고리 별 도서 조회 (에세이, 문학, 시...), 버튼?
 * GET /api/books?isbn= -> 도서 상세정보
 *
 * ✅ RecommendController (/api/recommendations) 뉴스 정보 기반 추천
 * GET /api/recommendations?keyword= -> 뉴스 키워드로 관련 서적 도출
 * GET /api/recommendations?category= -> 뉴스 카테고리별 책 추천
 * GET /api/recommendations?time= -> 해당 날짜 핫토픽 책 조회
 *
 * ✅ TbKeywordController (/api/keyword)
 * */
@RestController
@RequestMapping("/api/recommendations")
public class TbRecommendController {
//    @Autowired
//    private TbRecommendService recommendService;

//    @GetMapping
//    public List<Recommend> recommend() {
//
//    }

}

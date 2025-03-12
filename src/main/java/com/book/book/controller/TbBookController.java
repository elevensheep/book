package com.book.book.controller;

import com.book.book.dto.BookWithKeywordsDTO;
import com.book.book.dto.TbBookStoreDto;
import com.book.book.entity.*;
import com.book.book.repository.TbBookRepository;
import com.book.book.service.TbBookService;
import com.book.book.service.TbBookStoreService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * GET
 * /api/books?search=검색어
 *
 * Query String
 *
 * 도서 검색(제목 or 키워드) - 검색창 사용
 *
 *
 *
 * GET
 *
 * /api/books/category/{category}
 *
 * Path Variable
 *
 * 도서 카테고리별 조회 (에세이, 문학, 시...) - 버튼 사용
 *
 *
 *
 * GET
 *
 * /api/books/{isbn}
 *
 * Path Variable
 *
 * 특정 ISBN의 도서 상세 정보 조회
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("api/books")
public class TbBookController {
    private final TbBookService tbBookService;
    private final TbBookRepository tbBookRepository;
    private final TbBookStoreService tbBookStoreService;

    // /api/books/search?search=검색어, 도서 검색(제목) - 검색창 사용
    // full text index (n-gram parser 이용)쓸거임
    @GetMapping("/search")
    public ResponseEntity<?> search(@RequestParam(name = "search") String search) {
        System.out.println("검색어 : " + search);


        try {
            // tb_books 테이블에서 받은 검색어를 books_title에 포함시키는 모든 도서 검색
            List<TbBook> books = tbBookRepository.findByBookTitleContainingIgnoreCase(search);

            if (books.isEmpty()) {
                // 없으면 해당 검색어에 해당하는 도서가 없음을 출력
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("해당 검색어에 해당하는 도서가 없습니다.");
            }

            System.out.println("검색 결과 : " + books);
            // 프론트엔드로 검색 결과 전송
            return ResponseEntity.ok(books);

        } catch (Exception e) {
            // 예외 처리
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("검색 중 오류가 발생했습니다.");
        }

    }

    // /api/books/category/{category}, 도서 카테고리별 조회 (에세이, 문학, 시...) - 버튼 사용
    @GetMapping("/category/{category}")
    public ResponseEntity<?> searchByCategory(@PathVariable String category) {
        // tb_books 테이블에서 카테고리 일치하는거 다 가져와
        List<TbBook> result = tbBookRepository.findAllByBookCategory(category);

        if (!result.isEmpty()) {
            System.out.println(result);
            return ResponseEntity.ok(result); // 200 OK + JSON 반환
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("message", "해당 카테고리에 해당하는 도서가 없습니다."));
        }

    }

    private static final Logger log = LoggerFactory.getLogger(TbBookController.class);
    // http://localhost:8080/api/books/isbn/9788936434595
    // 특정 ISBN의 도서 상세 정보 조회
    // 상세페이지에 키워드랑 알라딘 포함
    @GetMapping("/isbn/{isbn}")
    public Mono<ResponseEntity<BookWithKeywordsDTO>> getBookWithKeywords(@PathVariable(name= "isbn") String isbn) {
        TbBook tbBook = tbBookService.getBookWithKeywords(isbn);

        BookWithKeywordsDTO bookWithKeywordsDTO = new BookWithKeywordsDTO(
                tbBook.getBookIsbn(),
                tbBook.getBookTitle(),
                tbBook.getBookPublisher(),
                tbBook.getBookAuthor(),
                tbBook.getBookImg(),
                tbBook.getBookDescription(),
                tbBook.getBookCategory(),
                tbBook.getKeywords().stream()
                        .map(TbBookKeyword::getBookKeyword)
                        .collect(Collectors.toList()) // 키워드 리스트로 변환

        );

        // 알라딘 API에서 매정 정보 가져오기


        // 키워드 포함 책 정보 리턴
        // TODO : 알라딘도 포함시켜서 리턴으로 수정
//        return ResponseEntity.ok(bookWithKeywordsDTO);
        return tbBookStoreService.fetchBookStores(isbn)
                .doFirst(() -> log.info("📌 fetchBookStores({}) 호출됨", isbn))
                .doOnNext(bookStoreResponse -> log.info("📌 알라딘 API 응답: {}", bookStoreResponse))
                .doOnTerminate(() -> log.info("📌 fetchBookStores 종료됨")) // ✅ 종료 로그 추가
                .switchIfEmpty(Mono.fromRunnable(() -> log.warn("🚨 fetchBookStores 응답이 비어 있음!")))                .map(bookStoreResponse -> {
                    bookWithKeywordsDTO.setBookStores(bookStoreResponse.getItemOffStoreList());
                    log.info("📌 최종 DTO: {}", bookWithKeywordsDTO);

                    return ResponseEntity.ok(bookWithKeywordsDTO);
                });
    }
}

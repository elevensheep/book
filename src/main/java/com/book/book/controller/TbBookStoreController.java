package com.book.book.controller;

import com.book.book.service.TbBookStoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TbBookStoreController {
    private final TbBookStoreService tbBookStoreService;

//    // http://localhost:8080/bookstore?ItemId=9791193506516
//    @GetMapping("/bookstore")
//    public void bookstore(@RequestParam(name = "ItemId") String ItemId) {
//        // 비동기 메서드를 호출하고, subscribe()로 실행을 시작합니다.
//        tbBookStoreService.fetchAndSaveData(ItemId)
//                .doOnTerminate(() -> System.out.println("API 호출 및 데이터 저장 작업이 종료되었습니다."))
//                .subscribe(); // 비동기 처리 시작
//    }
}

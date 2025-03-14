package com.book.book.api;

import com.book.book.service.LibraryApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LibraryApiRunner implements CommandLineRunner {

    private final LibraryApiService libraryApiService;

    @Autowired
    public LibraryApiRunner(LibraryApiService libraryApiService) {
        this.libraryApiService = libraryApiService;
    }

    @Override
    public void run(String... args) throws Exception {
        // getRecomisbn() 메서드를 호출하고 block()으로 동기적으로 결과를 받음
        List<String> recomisbnList = libraryApiService.getRecomisbn().block();

        // 결과가 null이 아니면 출력
        if (recomisbnList != null && !recomisbnList.isEmpty()) {
            System.out.println("추천된 ISBN 목록:");
            recomisbnList.forEach(isbn -> System.out.println(isbn));
        } else {
            System.out.println("추천된 ISBN이 없습니다.");
        }
    }
}

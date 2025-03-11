package com.book.book.controller;

import com.book.book.entity.TbBook;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.awt.print.Book;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class TbBookController {
//    private final TbBookService tbBookService;


//    // http://localhost:8080/search?query=
//    @GetMapping("/search")
//    public String fetchBooks(@RequestParam(name = "query") String query) {
//
//        tbBookService.fetchAndSaveData(query);
//
//        return query;
//    }
}

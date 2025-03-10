package com.book.book.controller;

import com.book.book.entity.TbBook;
import com.book.book.service.TbBookService;
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
    private final TbBookService tbBookService;


    @GetMapping("/search")
    public String fetchBooks(@RequestParam(name = "query") String query) {

        tbBookService.fetchAndSaveAllBooks(query);

        return query;
    }
}

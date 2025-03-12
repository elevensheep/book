package com.book.book.controller;

import com.book.book.entity.TbBook;
import com.book.book.entity.TbBookmark;
import com.book.book.entity.TbUser;
import com.book.book.repository.TbBookRepository;
import com.book.book.repository.TbBookmarkRepository;
import com.book.book.repository.TbUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bookmarks")
public class TbBookmarkController {
    private final TbUserRepository tbUserRepository;
    private final TbBookRepository tbBookRepository;
    private final TbBookmarkRepository tbBookmarkRepository;

    @PostMapping("{isbn}")
    public ResponseEntity addBookMark(@PathVariable("isbn") String isbn, @RequestBody Map<String, String> requestBody) {
        String userUuid = requestBody.get("userUuid");

        if (userUuid == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
        }

        TbBook book = tbBookRepository.findByBookIsbn(isbn);
        if (book == null) {
            return ResponseEntity.badRequest().body("해당 ISBN의 책이 존재하지 않습니다.");
        }

        Optional<TbUser> user = tbUserRepository.findByUserUuid(userUuid);
        if (user.isEmpty()) {
            return ResponseEntity.badRequest().body("해당 사용자가 존재하지 않습니다.");
        }

        TbBookmark bookmark = new TbBookmark();
        bookmark.setBook(book);
        bookmark.setUser(user);
        tbBookmarkRepository.save(bookmark);

        return ResponseEntity.ok("북마크에 추가되었습니다.");
    }


}

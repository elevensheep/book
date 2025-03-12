package com.book.book.repository;

import com.book.book.entity.TbBookmark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface TbBookmarkRepository extends JpaRepository<TbBookmark, Long> {

}

package com.springboot.booknest.repository;

import com.springboot.booknest.entity.Books;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BooksRepository extends JpaRepository<Books, Long> {

//    @Query("SELECT b FROM Books b WHERE b.bookname LIKE %:keyword% OR b.author LIKE %:keyword%")
//    List<Books> searchBooks(@Param("keyword") String keyword);
}

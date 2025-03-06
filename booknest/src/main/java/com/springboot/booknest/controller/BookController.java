package com.springboot.booknest.controller;

import com.springboot.booknest.entity.Books;
import com.springboot.booknest.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("getallbooks")
    public ResponseEntity<List<Books>> getAllBooks() {
        List<Books> books = bookService.getAllBooks();
        return ResponseEntity.ok(books);
    }

    @PostMapping("addbooks")
    public ResponseEntity<Books> addBooks(@RequestBody Books book) {
        bookService.addBooks(book.getId(), book.getBookname(), book.getAuthor(), book.getPrice());
        return ResponseEntity.status(201).body(book);
    }

    @PutMapping("update/{id}")
    public ResponseEntity<String> updateBook(@PathVariable Long id, @RequestBody Books book) {
        bookService.updateBook(id, book.getBookname(), book.getAuthor(), book.getPrice());
        return ResponseEntity.ok("Book updated successfully");
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable Long id){
        bookService.deleteBook(id);
        return ResponseEntity.ok("Book deleted successfully");
    }

//    @GetMapping("/search")
//    public ResponseEntity<List<Books>> searchBooks(@RequestParam("keyword") String keyword) {
//        List<Books> books = bookService.searchBooks(keyword);
//        return ResponseEntity.ok(books);
//    }
}
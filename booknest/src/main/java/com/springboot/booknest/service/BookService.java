package com.springboot.booknest.service;

import com.springboot.booknest.entity.Books;
import com.springboot.booknest.exception.ResourceNotFoundException;
import com.springboot.booknest.repository.BooksRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class BookService {

    private final BooksRepository booksRepository;

    public BookService(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    public List<Books> getAllBooks() {
        return booksRepository.findAll();
    }

    public void addBooks(Long id, String bookName, String author, double price) {
        Books book = new Books();
        book.setId(id);
        book.setBookname(bookName);
        book.setAuthor(author);
        book.setPrice(price);
        booksRepository.save(book);
    }

    public void updateBook(Long id, String bookName, String author, double price) {
        Optional<Books> optionalBook = booksRepository.findById(id);
        if (optionalBook.isPresent()) {
            Books book = optionalBook.get();
            if (bookName != null && !Objects.equals(book.getBookname(), bookName)) {
                book.setBookname(bookName);
            }
            if (author != null && !Objects.equals(book.getAuthor(), author)) {
                book.setAuthor(author);
            }
            if (price >= 0 && book.getPrice() != price) {
                book.setPrice(price);
            }
            booksRepository.save(book);
        } else {
            throw new ResourceNotFoundException("Book with id " + id + " not found");
        }

    }

    public void deleteBook(Long id) {
        Optional<Books> optionalBook = booksRepository.findById(id);
        if (optionalBook.isPresent()) {
            booksRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Book with id " + id + " not found");
        }
    }
//    public List<Books> searchBooks(String keyword) {
//        return booksRepository.searchBooks(keyword);
//    }


}
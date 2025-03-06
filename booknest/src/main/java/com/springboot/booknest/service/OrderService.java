package com.springboot.booknest.service;

import com.springboot.booknest.entity.Books;
import com.springboot.booknest.entity.Order;
import com.springboot.booknest.entity.Purchase;
import com.springboot.booknest.entity.Users;
import com.springboot.booknest.exception.ResourceNotFoundException;
import com.springboot.booknest.repository.BooksRepository;
import com.springboot.booknest.repository.OrderRepository;
import com.springboot.booknest.repository.PurchaseRepository;
import com.springboot.booknest.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final PurchaseRepository purchaseRepository;
    private final BooksRepository booksRepository;
    private final UserRepository userRepository;

    public OrderService(OrderRepository orderRepository, PurchaseRepository purchaseRepository, BooksRepository booksRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.purchaseRepository = purchaseRepository;
        this.booksRepository = booksRepository;
        this.userRepository = userRepository;
    }

    public Order orderBook(Long bookId, Long userId, int quantity) {
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + userId + " not found"));

        Order order = new Order();
        order.setBookId(bookId);
        order.setUserId(userId);
        order.setOrderDate(new Date());
        order.setQuantity(quantity);

        return orderRepository.save(order);
    }

    public Purchase buyBook(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order with id " + orderId + " not found"));

        Purchase purchase = new Purchase();
        purchase.setOrderId(orderId);
        purchase.setPurchaseDate(new Date());
        purchase.setTotalPrice(order.getQuantity() * getBookPrice(order.getBookId())); // Assuming getBookPrice() method exists

        return purchaseRepository.save(purchase);
    }

    private double getBookPrice(Long bookId) {
        Optional<Books> book = booksRepository.findById(bookId);
        if (book.isPresent()) {
            return book.get().getPrice();
        } else {
            throw new ResourceNotFoundException("Book not found with id " + bookId);
        }
    }
}
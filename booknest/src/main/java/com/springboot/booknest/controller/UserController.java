//package com.springboot.booknest.controller;
//
//import com.springboot.booknest.entity.Users;
//import com.springboot.booknest.service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/auth")
//public class UserController {
//
//    private final UserService userService;
//
//    @Autowired
//    public UserController(UserService userService) {
//        this.userService = userService;
//    }
//
//    @PostMapping
//    public ResponseEntity<Users> createUser(@RequestParam String username, @RequestParam String password, @RequestParam String roleName) {
//        Users user = userService.createUser(username, password, roleName);
//        return ResponseEntity.status(201).body(user);
//    }
//}
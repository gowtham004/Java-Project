package com.banking_app_1.JWT.token.controller;

import com.banking_app_1.JWT.token.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private JwtUtil jwtUtil;

    @Value("${role.admin}")
    private String roleAdmin;

    @Value("${role.user}")
    private String roleUser;

    private static final Logger logger = Logger.getLogger(UserController.class.getName());

    // To access user protected features
    @GetMapping("/protected-data")
    public ResponseEntity<String> protectedData(@RequestHeader("Authorization") String token) {
        if (token != null && token.startsWith("Bearer ")) {
            String jwtToken = token.substring(7);
            logger.info("Token received: " + jwtToken);

            try {
                if (jwtUtil.isTokenValid(jwtToken)) {
                    String username = jwtUtil.extractUserName(jwtToken);
                    Set<String> roles = jwtUtil.extractRoles(jwtToken);
                    logger.info("User: " + username + " Roles: " + roles);

                    if (roles.contains(roleAdmin)) {
                        return ResponseEntity.ok("Welcome " + username + " Role: " + roles);
                    } else if (roles.contains(roleUser)) {
                        return ResponseEntity.ok("Welcome user: " + username + " Role: " + roles);
                    } else {
                        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access Denied !!!");
                    }
                } else {
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid Token");
                }
            } catch (Exception e) {
                logger.severe("Token validation error: " + e.getMessage());
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid Token");
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authorization Header missing/invalid");
    }
}
package com.banking_app_1.JWT.token.controller;

import com.banking_app_1.JWT.token.dto.RegisterRequest;
import com.banking_app_1.JWT.token.entity.Role;
import com.banking_app_1.JWT.token.entity.User;
import com.banking_app_1.JWT.token.repository.RoleRepository;
import com.banking_app_1.JWT.token.repository.UserRepository;
import com.banking_app_1.JWT.token.security.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

@Controller
@RequestMapping("/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    private static final Logger logger = Logger.getLogger(AuthController.class.getName());

    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest registerRequest) {
        if (userRepository.findByUsername(registerRequest.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("User Name is already taken");
        }
        User newUser = new User();
        newUser.setUsername(registerRequest.getUsername());

        String encodedPassword = passwordEncoder.encode(registerRequest.getPassword());
        newUser.setPassword(encodedPassword);
        Set<Role> roles = new HashSet<>();
        if (registerRequest.getRoles() != null) {
            for (String roleName : registerRequest.getRoles()) {
                Role role = roleRepository.findByName(roleName).orElseGet(() -> {
                    Role newRole = new Role();
                    newRole.setName(roleName);
                    return roleRepository.save(newRole);
                });
                roles.add(role);
            }
        }
        newUser.setRoles(roles);
        userRepository.save(newUser);
        logger.info("User registered with roles: " + roles);
        return ResponseEntity.ok("User created successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User loginRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken
                    (
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    ));
        } catch (Exception e) {
            logger.severe("Authentication failed: " + e.getMessage());
            return ResponseEntity.status(401).body("Authentication failed");
        }
        String token = jwtUtil.generateToken(loginRequest.getUsername());
        return ResponseEntity.ok(token);
    }
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
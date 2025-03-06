package com.banking_app_1.JWT.token.security;

import com.banking_app_1.JWT.token.entity.Role;
import com.banking_app_1.JWT.token.entity.User;
import com.banking_app_1.JWT.token.repository.UserRepository;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Component
public class JwtUtil {

    private static final SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);
    private final int jwtExpirationTime = 86400000;

    private static final Logger logger = Logger.getLogger(JwtUtil.class.getName());

    private final UserRepository userRepository;

    public JwtUtil(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String generateToken(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            Set<Role> roles = user.get().getRoles();
            String rolesString = roles.stream()
                    .map(Role::getName)
                    .collect(Collectors.joining(","));
            return Jwts.builder()
                    .setSubject(username)
                    .claim("roles", rolesString)
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(new Date().getTime() + jwtExpirationTime))
                    .signWith(secretKey)
                    .compact();
        }
        throw new RuntimeException("User not found: " + username);
    }

    public String extractUserName(String token) {
        return Jwts.parser().setSigningKey(secretKey).build().parseClaimsJws(token).getBody().getSubject();
    }

    public Set<String> extractRoles(String token) {
        String rolesString = Jwts.parser().setSigningKey(secretKey).build().parseClaimsJws(token).getBody()
                .get("roles", String.class);
        return Set.of(rolesString.split(","));
    }

    public boolean isTokenValid(String token) {
        try {
            Jwts.parser().setSigningKey(secretKey).build().parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            logger.severe("Token validation failed: " + e.getMessage());
            return false;
        }
    }
}
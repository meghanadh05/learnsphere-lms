package com.caselearn.platform.service;

import com.caselearn.platform.dto.ApiDtos.*;
import com.caselearn.platform.entity.User;
import com.caselearn.platform.repository.UserRepository;
import com.caselearn.platform.security.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthService {
    private final UserRepository users;
    private final PasswordEncoder passwords;
    private final JwtService jwt;

    public AuthService(UserRepository users, PasswordEncoder passwords, JwtService jwt) {
        this.users = users; this.passwords = passwords; this.jwt = jwt;
    }

    public AuthResponse register(RegisterRequest request) {
        String email = request.email().trim().toLowerCase();
        if (users.existsByEmailIgnoreCase(email)) throw new ResponseStatusException(HttpStatus.CONFLICT, "An account with this email already exists.");
        User user = new User();
        user.setName(request.name().trim()); user.setEmail(email); user.setPasswordHash(passwords.encode(request.password()));
        users.save(user);
        return response(user);
    }

    public AuthResponse login(LoginRequest request) {
        User user = users.findByEmailIgnoreCase(request.email().trim())
                .orElseThrow(() -> new BadCredentialsException("Invalid credentials"));
        if (!passwords.matches(request.password(), user.getPasswordHash())) throw new BadCredentialsException("Invalid credentials");
        return response(user);
    }

    public User requireUser(String email) {
        return users.findByEmailIgnoreCase(email).orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Please sign in again."));
    }

    public static UserView view(User user) { return new UserView(user.getId(), user.getName(), user.getEmail()); }
    private AuthResponse response(User user) { return new AuthResponse(jwt.createToken(user.getEmail()), view(user)); }
}


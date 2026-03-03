/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * 
 * Filename: AuthService.java                                                      *
 * Project: NOLA Infrastructure Reporting & Tracking System                        *
 * Description: Handles registration logic (password validation & BCrypt hashing)  *
 *              and login logic (BCrypt verification & JWT token generation).      *
 * Author: Sophina Nichols                                                         *
 * Date Last Modified: 03/03/2026                                                  *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

package com.example.web.service;

import com.example.web.dto.LoginRequest;
import com.example.web.dto.RegisterRequest;
import com.example.web.model.User;
import com.example.web.repository.UserRepository;
import com.example.web.util.JwtUtil;
import com.example.web.util.PasswordUtil;

/* AuthService contains all user authentication logic.
 * Tasks:
 *      - Validate registration input (username length, email format,
 *        and password strength)
 *      - Check database for duplicate usernames or emails
 *      - Hash passwords with BCrypt (cost factor 12)
 *      - Verify BCrypt password hashes on login
 *      - Generate JWT tokens for authenticated users
 *
 * This class does NOT handle HTTP requests or responses, that is
 * the responsibility of AuthController.
 */

public class AuthService {

    // Repository used to query and save users in the database
    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    // Registration 
    public User register(RegisterRequest request) throws Exception {

        if (request.getUsername() == null || request.getUsername().trim().length() < 3) {
            throw new Exception("Username must be at least 3 characters");
        }

        if (request.getEmail() == null || !request.getEmail().contains("@")) {
            throw new Exception("Invalid email address");
        }

        String password = request.getPassword();
        if (password == null || password.length() < 8) {
            throw new Exception("Password must be at least 8 characters");
        }
        if (!password.matches(".*[A-Z].*")) {
            throw new Exception("Password must contain at least one uppercase letter");
        }
        if (!password.matches(".*[0-9].*")) {
            throw new Exception("Password must contain at least one number");
        }
        if (!password.matches(".*[!@#$%^&*()].*")) {
            throw new Exception("Password must contain at least one special character");
        }

        if (userRepository.findByUsername(request.getUsername()) != null) {
            throw new Exception("Username already taken");
        }
        if (userRepository.findByEmail(request.getEmail()) != null) {
            throw new Exception("Email already registered");
        }

        String hashedPassword = PasswordUtil.hashPassword(request.getPassword());

        User newUser = new User();
        newUser.setUsername(request.getUsername());
        newUser.setEmail(request.getEmail());
        newUser.setPhone(null);
        newUser.setPasswordHash(hashedPassword);
        newUser.setRole("Citizen");     // New accounts default to role "Citizen"
        newUser.setActive(true);

        return userRepository.save(newUser);
    }

    // Authentication
    public String login(LoginRequest request) throws Exception {

        User user = userRepository.findByEmail(request.getEmail())
        .orElseThrow(() -> new Exception("Invalid email or password"));

        if (!PasswordUtil.verifyPassword(request.getPassword(), user.getPasswordHash())) {
            throw new Exception("Invalid email or password");
        }
        
        return JwtUtil.generateToken(user.getId(), user.getUsername(), user.getRole());
    }
}

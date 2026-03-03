 /**************************************************************************
 * Filename: AuthController.java
 * Project: Infrastructure Reporting & Tracking System
 * Description: Exposes authentication endpoints for user registration and
 *              login, returning JWT tokens upon successful authentication.
 * Author: Sophina Nichols
 * Date Last Modified: 03/03/2026
 **************************************************************************/


// This is what frontend will call
package com.example.web.controller;

import com.example.web.dto.LoginRequest;
import com.example.web.dto.RegisterRequest;
import com.example.web.model.User;
import com.example.web.repository.UserRepository;
import com.example.web.service.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/api/auth/*")
public class AuthController extends HttpServlet {

    private AuthService authService;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void init() {
        try {
            // Load env variables
            String url = System.getenv("DB_URL");
            String user = System.getenv("DB_USER");
            String password = System.getenv("DB_PASSWORD");

            Connection connection = DriverManager.getConnection(url, user, password);
            UserRepository userRepository = new UserRepository(connection);
            authService = new AuthService(userRepository);
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize AuthController", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        String path = req.getPathInfo();

        if ("/register".equals(path)) {
            handleRegister(req, resp);
        } else if ("/login".equals(path)) {
            handleLogin(req, resp);
        } else {
            resp.setStatus(404);
            resp.getWriter().write("{\"error\": \"Not found\"}");
        }
    }

    private void handleRegister(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            RegisterRequest request = objectMapper.readValue(req.getInputStream(), RegisterRequest.class);
            User user = authService.register(request);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Registration successful");
            response.put("username", user.getUsername());

            resp.setStatus(201);
            resp.getWriter().write(objectMapper.writeValueAsString(response));
        } catch (Exception e) {
            resp.setStatus(400);
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", e.getMessage());
            resp.getWriter().write(objectMapper.writeValueAsString(error));
        }
    }

    private void handleLogin(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            LoginRequest request = objectMapper.readValue(req.getInputStream(), LoginRequest.class);
            String token = authService.login(request);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("token", token);

            resp.setStatus(200);
            resp.getWriter().write(objectMapper.writeValueAsString(response));
        } catch (Exception e) {
            resp.setStatus(401);
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", e.getMessage());
            resp.getWriter().write(objectMapper.writeValueAsString(error));
        }
    }
}
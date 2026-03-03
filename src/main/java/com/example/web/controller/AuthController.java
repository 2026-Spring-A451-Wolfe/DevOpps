 /**************************************************************************
 * Filename: AuthController.java
 * Project: Infrastructure Reporting & Tracking System
 * Description: Handles authentication endpoints for user registration and
 *              login, returning JWT tokens upon successful authentication.
 * Author: Sophina Nichols
 * Date Last Modified: 03/03/2026
 **************************************************************************/

package com.example.web.controller;

import com.example.web.dto.LoginRequest;
import com.example.web.dto.RegisterRequest;
import com.example.web.model.User;
import com.example.web.repository.UserRepository;
import com.example.web.service.AuthService;
import com.example.web.util.DatabaseUtil;
import com.example.web.util.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/api/auth/*")
public class AuthController extends HttpServlet {

    private AuthService authService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void init() {
        UserRepository userRepository = new UserRepository(DatabaseUtil.getDataSource());
        authService = new AuthService(userRepository);
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

    // GET /api/auth/me — returns current user info from JWT
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        String path = req.getPathInfo();

        if ("/me".equals(path)) {
            handleMe(req, resp);
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

    private void handleMe(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            String authHeader = req.getHeader("Authorization");
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                resp.setStatus(401);
                resp.getWriter().write("{\"error\": \"Missing or invalid token\"}");
                return;
            }

            String token = authHeader.substring(7);
            long userId = JwtUtil.getUserId(token);
            String role = JwtUtil.getRole(token);

            Map<String, Object> response = new HashMap<>();
            response.put("userId", userId);
            response.put("role", role);

            resp.setStatus(200);
            resp.getWriter().write(objectMapper.writeValueAsString(response));
        } catch (Exception e) {
            resp.setStatus(401);
            resp.getWriter().write("{\"error\": \"Invalid or expired token\"}");
        }
    }
}
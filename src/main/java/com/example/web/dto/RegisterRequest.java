 /**************************************************************************
 * Filename: RegisterRequest.java
 * Project: Infrastructure Reporting & Tracking System
 * Description: Data Transfer Object used to capture user registration details
 *               before creating a new system account.
 * Author: Sophina Nichols
 * Date Last Modified: 03/03/2026
 **************************************************************************/

// Represents the data a user sends when signing up 
package com.example.web.dto;

public class RegisterRequest {
    private String username;
    private String email;
    private String password;

    public RegisterRequest() {}

    // Getters and Setters
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}

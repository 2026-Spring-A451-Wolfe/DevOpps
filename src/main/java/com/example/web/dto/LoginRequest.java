 /**************************************************************************
 * Filename: LoginRequest.java
 * Project: Infrastructure Reporting & Tracking System
 * Description: Data Transfer Object used to capture user login credentials
 *              submitted to the authentication endpoint.
 * Author: Sophina Nichols
 * Date Last Modified: 03/03/2026
 **************************************************************************/

package com.example.web.dto;

public class LoginRequest {
    private String email;
    private String password;

    public LoginRequest() {}

    // Getters and Setters
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    
}

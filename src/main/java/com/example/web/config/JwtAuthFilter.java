/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Filename: JwtAuthFilter.java                                                      *
 * Project: NOLA Infrastructure Reporting & Tracking System                          *
 * Description: Servlet filter that intercepts all incoming HTTP requests and        *
 *              validates the JWT token from the Authorization header before         *
 *              allowing access to protected endpoints. Extracts and verifies the.   *
 *              token using JwtUtil, rejects requests with missing or invalid tokens *
 *              with 401 Unauthorized, and attaches the decoded user claims to the   *
 *              request for downstream use by controllers. Registered in web.xml.    *
 * Author: Sophina Nichols                                                           *
 * Date Last Modified: 03/05/2026                                                    *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

package com.example.web.config;

public class JwtAuthFilter {
    
}

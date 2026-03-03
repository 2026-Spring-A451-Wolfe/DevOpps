 /**************************************************************************
 * Filename: PasswordUtil.java
 * Project: Infrastructure Reporting & Tracking System
 * Description: Provides secure password hashing and verification utilities
 *              to protect user credentials.
 * Author: Sophina Nichols
 * Date Last Modified: 03/03/2026
 **************************************************************************/

package com.example.web.util;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtil {
    // Hashes a plain text password
    public static String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt(12));
    }

    // Checks a plain text password against a hashed password
    public static boolean verifyPassword(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }
    
}

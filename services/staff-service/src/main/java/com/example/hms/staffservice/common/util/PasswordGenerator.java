package com.example.hms.staffservice.common.util;

import java.security.SecureRandom;

public class PasswordGenerator {
    private static final String LOWER = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String DIGITS = "0123456789";
    private static final String SPECIAL = "!@#$%^&*()_-+=<>?";
    private static final String ALL = LOWER + UPPER + DIGITS + SPECIAL;
    private static final SecureRandom random = new SecureRandom();
    
    public static String generateSecurePassword(int length) {
        if (length < 8) {
            length = 8; // Minimum secure password length
        }
        
        char[] password = new char[length];
        
        // Ensure at least one character from each category
        password[0] = LOWER.charAt(random.nextInt(LOWER.length()));
        password[1] = UPPER.charAt(random.nextInt(UPPER.length()));
        password[2] = DIGITS.charAt(random.nextInt(DIGITS.length()));
        password[3] = SPECIAL.charAt(random.nextInt(SPECIAL.length()));
        
        // Fill the rest with random characters
        for (int i = 4; i < length; i++) {
            password[i] = ALL.charAt(random.nextInt(ALL.length()));
        }
        
        // Shuffle the password
        for (int i = 0; i < password.length; i++) {
            int randomIndex = random.nextInt(password.length);
            char temp = password[i];
            password[i] = password[randomIndex];
            password[randomIndex] = temp;
        }
        
        return new String(password);
    }
} 
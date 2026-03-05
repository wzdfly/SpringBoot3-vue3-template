package com.example.test;

public class SimplePasswordTest {
    public static void main(String[] args) {
        String rawPassword = "123456";
        String hashedPassword = "$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iKWTn5HtUfHOKMxbqIlqZpbNDIgS";
        
        System.out.println("Raw password: " + rawPassword);
        System.out.println("Hashed password from DB: " + hashedPassword);
        System.out.println("Hash format looks correct: " + hashedPassword.startsWith("$2a$"));
        
        // 检查密码格式
        if (hashedPassword.startsWith("$2a$") && hashedPassword.length() == 60) {
            System.out.println("Password format is valid BCrypt format");
        } else {
            System.out.println("Password format is NOT valid BCrypt format");
        }
    }
}
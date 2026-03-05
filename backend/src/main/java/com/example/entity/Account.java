package com.example.entity;


import lombok.Data;

@Data
public class Account {
    int id;
    String username;
    String password;
    String mail;
    private String role;
    
    // 添加 getter 和 setter
    public String getRole() {
        return role;
    }
    
    public void setRole(String role) {
        this.role = role;
    }
}

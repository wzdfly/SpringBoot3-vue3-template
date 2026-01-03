package com.example.service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface AdminService {
    
    // 管理员添加用户
    String addUser(String username, String email, String role);
    
    // 获取所有用户列表
    List<Map<String, Object>> getAllUsers();
    
    // 删除用户
    boolean deleteUser(int id);
    
    // 更新用户信息
    String updateUser(int id, String username, String email, String role);
}
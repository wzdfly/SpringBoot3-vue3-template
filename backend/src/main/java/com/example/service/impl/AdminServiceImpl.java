package com.example.service.impl;

import com.example.entity.Account;
import com.example.mapper.UserMapper;
import com.example.service.AdminService;
import jakarta.annotation.Resource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AdminServiceImpl implements AdminService {

    @Resource
    UserMapper userMapper;

    @Resource
    BCryptPasswordEncoder encoder;

    @Override
    public String addUser(String username, String email, String role) {
        // 检查用户名或邮箱是否已存在
        Account existingAccount = userMapper.findAccountByNameOrEmail(username);
        if (existingAccount != null) {
            if (existingAccount.getUsername().equals(username)) {
                return "用户名已存在，请选择其他用户名";
            }
            if (existingAccount.getMail().equals(email)) {
                return "邮箱已存在，请选择其他邮箱";
            }
        }

        // 验证角色
        if (!"SYS_ADMIN".equals(role) && !"ADMIN".equals(role) && !"DORM_ADMIN".equals(role) && !"STUDENT".equals(role)) {
            return "角色参数无效";
        }

        // 生成默认密码并加密
        String defaultPassword = "123456";
        String encodedPassword = encoder.encode(defaultPassword);

        try {
            // 创建用户账户
            int result = userMapper.createAccountWithRole(username, encodedPassword, email, role);
            if (result > 0) {
                return null; // 成功
            } else {
                return "创建用户失败";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "内部错误，请联系管理员";
        }
    }

    @Override
    public List<Map<String, Object>> getAllUsers() {
        List<Account> accounts = userMapper.findAllAccounts();
        List<Map<String, Object>> users = new ArrayList<>();
        
        for (Account account : accounts) {
            Map<String, Object> user = new HashMap<>();
            user.put("id", account.getId());
            user.put("username", account.getUsername());
            user.put("email", account.getMail());
            user.put("role", account.getRole() != null ? account.getRole() : "STUDENT");
            // 这里可以添加创建时间，如果数据库表有该字段
            user.put("createTime", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            users.add(user);
        }
        
        return users;
    }

    @Override
    public boolean deleteUser(int id) {
        try {
            int result = userMapper.deleteAccountById(id);
            return result > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public String updateUser(int id, String username, String email, String role) {
        // 检查用户是否存在
        Account existingAccount = userMapper.findAccountById(id);
        if (existingAccount == null) {
            return "用户不存在";
        }

        // 检查用户名或邮箱是否被其他用户使用
        Account conflictAccount = userMapper.findAccountByNameOrEmail(username);
        if (conflictAccount != null && conflictAccount.getId() != id) {
            if (conflictAccount.getUsername().equals(username)) {
                return "用户名已被其他用户使用";
            }
            if (conflictAccount.getMail().equals(email)) {
                return "邮箱已被其他用户使用";
            }
        }

        // 验证角色
        if (!"SYS_ADMIN".equals(role) && !"ADMIN".equals(role) && !"DORM_ADMIN".equals(role) && !"STUDENT".equals(role)) {
            return "角色参数无效";
        }

        try {
            int result = userMapper.updateAccount(id, username, email, role);
            if (result > 0) {
                return null; // 成功
            } else {
                return "更新用户失败";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "内部错误，请联系管理员";
        }
    }
}
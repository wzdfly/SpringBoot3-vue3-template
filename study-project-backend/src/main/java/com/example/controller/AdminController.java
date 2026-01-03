package com.example.controller;

import com.example.entity.RestBean;
import com.example.service.AdminService;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.Pattern;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final String EMAIL_REGEX = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";
    private final String USERNAME_REGEX = "^[\u4e00-\u9fa5a-zA-Z0-9_-]{3,20}$";

    @Resource
    AdminService adminService;

    // 管理员添加用户
    @PostMapping("/users")
    public RestBean<String> addUser(@Pattern(regexp = USERNAME_REGEX) @RequestParam("username") String username,
                                   @Pattern(regexp = EMAIL_REGEX) @RequestParam("email") String email,
                                   @RequestParam("role") String role) {
        String result = adminService.addUser(username, email, role);
        if (result == null) {
            return RestBean.success("用户添加成功");
        } else {
            return RestBean.failure(400, result);
        }
    }

    // 获取所有用户列表
    @GetMapping("/users")
    public RestBean<List<Map<String, Object>>> getAllUsers() {
        List<Map<String, Object>> users = adminService.getAllUsers();
        return RestBean.success(users);
    }

    // 删除用户
    @DeleteMapping("/users/{id}")
    public RestBean<String> deleteUser(@PathVariable("id") int id) {
        boolean result = adminService.deleteUser(id);
        if (result) {
            return RestBean.success("用户删除成功");
        } else {
            return RestBean.failure(400, "删除用户失败");
        }
    }

    // 更新用户信息
    @PutMapping("/users/{id}")
    public RestBean<String> updateUser(@PathVariable("id") int id,
                                      @Pattern(regexp = USERNAME_REGEX) @RequestParam("username") String username,
                                      @Pattern(regexp = EMAIL_REGEX) @RequestParam("email") String email,
                                      @RequestParam("role") String role) {
        String result = adminService.updateUser(id, username, email, role);
        if (result == null) {
            return RestBean.success("用户更新成功");
        } else {
            return RestBean.failure(400, result);
        }
    }
}
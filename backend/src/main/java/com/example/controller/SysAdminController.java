package com.example.controller;

import com.example.entity.RestBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sys-admin")
public class SysAdminController {
    
    @GetMapping("/dashboard")
    public RestBean<String> dashboard() {
        return RestBean.success("系统管理员控制台数据");
    }
}

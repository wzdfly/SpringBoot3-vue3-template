package com.example.controller;

import com.example.entity.RestBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/student")
public class StudentController {
    
    @GetMapping("/dashboard")
    public RestBean<String> dashboard() {
        return RestBean.success("学生端控制台数据");
    }
}

package com.example.controller;

import com.example.entity.RestBean;
import com.example.service.AuthorizeService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthorizeController {

    //正则表达式
    private final String EMAIL_REGEX = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";
    private final String USERNAME_REGEX = "^[\u4e00-\u9fa5a-zA-Z0-9_-]{4,16}$";

    @Resource
    AuthorizeService service;
    
    @Resource
    AuthenticationManager authenticationManager;

    // 登录接口
    @PostMapping("/login")
    public RestBean<String> login(@RequestBody Map<String, Object> loginData, HttpServletRequest request) {
        String username = (String) loginData.get("username");
        String password = (String) loginData.get("password");
        // Boolean remember = (Boolean) loginData.get("remember");
        
        if (username == null || password == null || username.trim().isEmpty() || password.trim().isEmpty()) {
            return RestBean.failure(400, "用户名和密码不能为空");
        }
        
        try {
            // 创建认证令牌
            UsernamePasswordAuthenticationToken authToken = 
                new UsernamePasswordAuthenticationToken(username, password);
            
            // 进行认证
            Authentication authentication = authenticationManager.authenticate(authToken);
            
            // 设置安全上下文
            SecurityContextHolder.getContext().setAuthentication(authentication);
            
            // 创建会话并保存安全上下文
            HttpSession session = request.getSession(true);
            session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, 
                                SecurityContextHolder.getContext());
            
            return RestBean.success("登录成功");
        } catch (AuthenticationException e) {
            return RestBean.failure(401, "用户名或密码错误");
        }
    }

    // 获取当前用户信息
    @PostMapping("/me")
    public RestBean<String> me() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && 
            !"anonymousUser".equals(authentication.getName())) {
            return RestBean.success("当前用户: " + authentication.getName());
        }
        return RestBean.failure(401, "未登录");
    }

    //邮箱验证
    @PostMapping("/valid-register-email")
    public RestBean<String> validateRegisterEmail(@Pattern (regexp = EMAIL_REGEX )@RequestParam("email") String email, HttpSession session){


        String s =  service.sendValidateEmail(email,session.getId(),false);
        if(s == null)
            return RestBean.success("邮件已发送,请注意查收");
        else
            return RestBean.failure(400,s);
    }

    @PostMapping("/valid-reset-email")
    public RestBean<String> validateResetEmail(@Pattern (regexp = EMAIL_REGEX )@RequestParam("email") String email, HttpSession session){
        String s =  service.sendValidateEmail(email,session.getId(),true);
        if(s == null)
            return RestBean.success("邮件已发送,请注意查收");
        else
            return RestBean.failure(400,s);
    }


    //用户注册
    @PostMapping("/register")
    public RestBean<String> registerUser(@Pattern(regexp = USERNAME_REGEX)@RequestParam("username") String username,
                                         @Length(min = 6,max = 16) @RequestParam("password") String password,
                                         @RequestParam("email") String email,
                                         @Length(min = 6,max = 6) @RequestParam("code") String code,
                                         HttpSession session){
        String s = service.validateAndRegister(username,password,email,code,session.getId());
        if(s == null){
            return RestBean.success("注册成功");
        }else {
            return RestBean.failure(400, s);
        }
    }

    @PostMapping("/start-rest")
    public RestBean<String> startRest(@RequestParam("email") String email,
                                      @Length(min = 6,max = 6) @RequestParam("code") String code,
                                      HttpSession session){
        String s = service.validateOnly(email,code,session.getId());
        if(s == null){
            session.setAttribute("rest-password",email);
            return RestBean.success();
        }else {
            return RestBean.failure(400,s);
        }
    }

    @PostMapping("/do-rest")
    public RestBean<String> restPassword(@Length(min = 6,max = 16) @RequestParam("password") String password,HttpSession session){
        String email = (String)session.getAttribute("rest-password");
        if(email == null){
            return RestBean.failure(401,"请先进行邮箱验证");
        }else if(service.resetPassword(password,email)){
            session.removeAttribute("reset-password");
            return RestBean.success("密码重置成功");
        }else{
            return RestBean.failure(500,"内部错误，请联系管理员");
        }
    }

}

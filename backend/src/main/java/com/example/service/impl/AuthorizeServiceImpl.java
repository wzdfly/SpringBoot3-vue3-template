package com.example.service.impl;

import com.example.entity.Account;
import com.example.entity.EmailVerificationCode;
import com.example.mapper.UserMapper;
import com.example.mapper.EmailVerificationCodeMapper;
import com.example.service.AuthorizeService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class AuthorizeServiceImpl implements AuthorizeService {

    @Value("${spring.mail.username}")
    String from;

    @Resource
    UserMapper mapper;

    @Resource
    EmailVerificationCodeMapper codeMapper;

    @Resource
    MailSender mailSender;

    // 使用@Lazy注解延迟加载，避免循环依赖
    @Resource
    @org.springframework.context.annotation.Lazy
    BCryptPasswordEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("尝试登录的用户: " + username);
        if(username == null)
            throw new UsernameNotFoundException("用户名不能为空");
        Account account = mapper.findAccountWithRoleByUsername(username);
        if(account == null) {
            System.out.println("数据库中未找到用户: " + username);
            throw new UsernameNotFoundException("用户名或密码错误");
        }
        System.out.println("数据库查出的信息: " + account);
        // 根据数据库中的角色设置权限
        String role = account.getRole() != null ? account.getRole() : "USER";
        return User
                .withUsername(account.getUsername())
                .password(account.getPassword())
                .roles(role)  // 使用数据库中的角色
                .build();
    }

    @Override
    public String sendValidateEmail(String email, String sessionId, boolean hasAccount){
        // 检查是否存在有效的验证码
        EmailVerificationCode existingCode = codeMapper.findValidCode(email, sessionId, hasAccount);
        if(existingCode != null){
            // 检查是否在2分钟内重复请求
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime twoMinutesAgo = now.minusMinutes(2);
            if(existingCode.getCreateTime().isAfter(twoMinutesAgo)){
                return "请求频繁，请稍后再试";
            }
        }
        
        Account account = mapper.findAccountByNameOrEmail(email);
        if(hasAccount && account == null)
            return "此邮箱没有注册";
        if(!hasAccount && account != null){
            return "此邮箱已被其他用户注册";
        }

        Random random = new Random();
        int code = random.nextInt(900000) + 100000; // 确保生成6位数字
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(email);
        message.setSubject("您的验证邮件");
        message.setText("您好，欢迎注册宿舍管理系统，验证码为：" + code + "，如果您意外收到此邮件，请忽略。");
        
        try{
            mailSender.send(message);
            // 先删除旧的验证码
            codeMapper.deleteCode(email, sessionId, hasAccount);
            // 插入新的验证码，有效期3分钟
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime expireTime = now.plusMinutes(3);
            codeMapper.insertCode(email, String.valueOf(code), sessionId, hasAccount, now, expireTime);
            return null;
        } catch (MailException e){
            e.printStackTrace();
        }
        return "邮件发送失败，请联系管理员，检查邮件地址是否有效";
    }

    @Override
    public String validateAndRegister(String username, String password, String email, String code, String sessionId){
        EmailVerificationCode verificationCode = codeMapper.findValidCode(email, sessionId, false);
        if(verificationCode != null){
            if(verificationCode.getCode().equals(code)){
                // 标记验证码为已使用，而不是删除
                codeMapper.markCodeAsUsed(email, sessionId, false, code);
                Account account = mapper.findAccountByNameOrEmail(username);
                password = encoder.encode(password);
                if(account != null) return "此用户名已被注册，请更换用户名";
                if(mapper.createAccount(username, password, email) > 0){
                    return null;
                } else {
                    return "内部错误，请联系管理员";
                }
            } else {
                return "验证码错误，请检查后再提交";
            }
        } else {
            return "验证码失效或已使用，请重新请求";
        }
    }

    @Override
    public String validateOnly(String email, String code, String sessionId){
        EmailVerificationCode verificationCode = codeMapper.findValidCode(email, sessionId, true);
        if(verificationCode != null){
            if(verificationCode.getCode().equals(code)){
                // 标记验证码为已使用
                codeMapper.markCodeAsUsed(email, sessionId, true, code);
                return null;
            } else {
                return "验证码错误，请检查后再提交";
            }
        } else {
            return "验证码失效或已使用，请重新请求";
        }
    }

    @Override
    public boolean resetPassword(String password, String email){
        password = encoder.encode(password);
        return mapper.resetPasswordByEmail(password, email) > 0;
    }
}

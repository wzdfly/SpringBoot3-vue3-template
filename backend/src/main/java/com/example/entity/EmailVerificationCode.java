package com.example.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class EmailVerificationCode {
    private Long id;
    private String mail;
    private String code;
    private String sessionId;
    private Boolean hasAccount;
    private LocalDateTime createTime;
    private LocalDateTime expireTime;
    private Boolean used;  
    private LocalDateTime usedTime; 
}
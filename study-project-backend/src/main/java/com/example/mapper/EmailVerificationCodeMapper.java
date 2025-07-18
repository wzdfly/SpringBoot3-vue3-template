package com.example.mapper;

import com.example.entity.EmailVerificationCode;
import org.apache.ibatis.annotations.*;
import java.time.LocalDateTime;

@Mapper
public interface EmailVerificationCodeMapper {
    
    @Select("SELECT * FROM email_verification_codes WHERE email = #{email} AND session_id = #{sessionId} AND has_account = #{hasAccount} AND expire_time > NOW() AND used = false")
    EmailVerificationCode findValidCode(String email, String sessionId, Boolean hasAccount);
    
    @Insert("INSERT INTO email_verification_codes (email, code, session_id, has_account, create_time, expire_time, used) VALUES (#{email}, #{code}, #{sessionId}, #{hasAccount}, #{createTime}, #{expireTime}, false)")
    int insertCode(String email, String code, String sessionId, Boolean hasAccount, LocalDateTime createTime, LocalDateTime expireTime);
    
    @Update("UPDATE email_verification_codes SET used = true, used_time = NOW() WHERE email = #{email} AND session_id = #{sessionId} AND has_account = #{hasAccount} AND code = #{code}")
    int markCodeAsUsed(String email, String sessionId, Boolean hasAccount, String code);
    
    @Delete("DELETE FROM email_verification_codes WHERE email = #{email} AND session_id = #{sessionId} AND has_account = #{hasAccount}")
    int deleteCode(String email, String sessionId, Boolean hasAccount);
    
    @Delete("DELETE FROM email_verification_codes WHERE expire_time < NOW()")
    int deleteExpiredCodes();
    
    @Delete("DELETE FROM email_verification_codes WHERE used = true AND used_time < DATE_SUB(NOW(), INTERVAL 1 DAY)")
    int deleteOldUsedCodes();
}
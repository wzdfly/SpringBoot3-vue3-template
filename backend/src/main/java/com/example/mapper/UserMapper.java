package com.example.mapper;

import com.example.entity.Account;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {
    @Select("select * from user where username = #{text} or mail = #{text}")
    Account findAccountByNameOrEmail(String text);
    
    // 添加根据用户名查询用户（包含角色）的方法
    @Select("select id, username, password, mail, role from user where username = #{username}")
    Account findAccountWithRoleByUsername(String username);

    @Insert("insert into user (username,password,mail) values(#{username},#{password},#{mail})")
    int createAccount(String username,String password,String mail);

    @Update("update user set password = #{password} where mail = #{mail}")
    int resetPasswordByEmail(String password,String mail);
    
    // 管理员功能：创建带角色的用户
    @Insert("insert into user (username,password,mail,role) values(#{username},#{password},#{mail},#{role})")
    int createAccountWithRole(String username, String password, String mail, String role);
    
    // 管理员功能：获取所有用户
    @Select("select id, username, password, mail, role from user")
    List<Account> findAllAccounts();
    
    // 管理员功能：根据ID查询用户
    @Select("select id, username, password, mail, role from user where id = #{id}")
    Account findAccountById(int id);
    
    // 管理员功能：删除用户
    @Delete("delete from user where id = #{id}")
    int deleteAccountById(int id);
    
    // 管理员功能：更新用户信息
    @Update("update user set username = #{username}, mail = #{mail}, role = #{role} where id = #{id}")
    int updateAccount(int id, String username, String mail, String role);
}

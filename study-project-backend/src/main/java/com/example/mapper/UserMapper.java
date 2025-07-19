package com.example.mapper;

import com.example.entity.Account;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;


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
}

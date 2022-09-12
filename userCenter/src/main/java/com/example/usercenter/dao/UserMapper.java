package com.example.usercenter.dao;

import com.example.usercenter.bean.pojo.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper extends BaseMapper<User>{
    User userLogin(@Param("phone")String phone,@Param("password") String password);

    int checkPhone(@Param("phone")String phone);

    int insertUser(@Param("phone")String phone, @Param("password")String password);
}

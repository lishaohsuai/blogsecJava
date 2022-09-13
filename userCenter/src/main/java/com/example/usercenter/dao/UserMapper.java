package com.example.usercenter.dao;

import com.example.usercenter.bean.pojo.User;
import com.example.usercenter.dataobject.OptCodeDo;
import io.lettuce.core.dynamic.annotation.Param;

public interface UserMapper extends BaseMapper<User> {
    User userLogin(@Param("phone") String phone, @Param("password") String password);

    User userLoginByPhone(@Param("phone") String phone);

    int checkPhone(@Param("phone") String phone);

    int insertUser(@Param("phone") String phone, @Param("password") String password);

    int insertOtpCode(@Param("phone") String phone, @Param("optCode") String otpCode,
                      @Param("updateTime") String updateTime);

    OptCodeDo getOtpCodeByPhone(@Param("phone") String phone);
}
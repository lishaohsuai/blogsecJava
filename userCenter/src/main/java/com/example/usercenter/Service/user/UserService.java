package com.example.usercenter.Service.user;

import com.example.usercenter.bean.pojo.User;
import com.example.usercenter.common.ServerResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.ParseException;


public interface UserService {
    ServerResponse<User> login(String phone, String password);
    ServerResponse<User> loginUseOtpCode(String phone, String otpCode);
    ServerResponse<String> getOpt(String phone);
    ServerResponse<String> register(String phone, String password);
}

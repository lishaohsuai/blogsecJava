package com.example.usercenter.controller.user;

import com.example.usercenter.Service.user.UserService;
import com.example.usercenter.bean.pojo.User;
import com.example.usercenter.common.ServerResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Qualifier
    private RedisTemplate redisTemplate;

    @PostMapping("/login")
    public ServerResponse<User> login(String phone, String password, HttpSession session) {
        log.info("登陆->用户手机号:{}", phone);
        ServerResponse<User> response = userService.login(phone, password);
        if(response.isSuccess()) {
            //生成登录凭证token，UUID
            String uuidToken = UUID.randomUUID().toString();
            uuidToken = uuidToken.replace("-","");
            User user = response.getData();
            user.setPassword(""); // 不应该返回前端密码
            response.setData(user);
            //建议token和用户登陆态之间的联系
            redisTemplate.opsForValue().set(uuidToken,response.getData());
            redisTemplate.expire(uuidToken,1, TimeUnit.HOURS);
        }
        return response;
    }

    /**
     * 用户注册
     * @return ServerResponse
     */
    @PostMapping("/register")
    public ServerResponse<String> register(String phone, String password) {
        log.info("注册->用户手机{}, 用户密码{}", phone, password);
        return userService.register(phone, password);
    }


}

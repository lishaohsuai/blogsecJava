package com.example.usercenter.serviceimpl;

import com.example.usercenter.Service.user.UserService;
import com.example.usercenter.bean.pojo.User;
import com.example.usercenter.common.ResponseCode;
import com.example.usercenter.common.ServerResponse;
import com.example.usercenter.dao.UserMapper;
import com.example.usercenter.utils.Md5Util;
import io.netty.util.internal.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public ServerResponse<User> login(String phone, String password) {
        //通过用户的手机获取用户信息
        String md5Password = Md5Util.md5EncodeUtf8(password);
        User user = userMapper.userLogin(phone, md5Password);
        if(user == null) {
            log.warn("用户手机号：{}，登陆失败密码错误", phone);
            return ServerResponse.createByErrorMessage("密码错误");
        }
        user.setPassword(StringUtils.EMPTY);
        log.info("用户手机号：{}，登陆成功", phone);
        return ServerResponse.createBySuccess("登陆成功", user);
    }

    @Override
    public ServerResponse<String> getOpt(String phone){
        Random random = new Random();
        int randomInt = random.nextInt(8999);
        randomInt += 1000;
        String otpCode = String.valueOf(randomInt);
        // 将 otp Code 放入数据表中
        return ServerResponse.createBySuccess();
    }

    @Override
    public ServerResponse<String> register(String phone, String password){
        // 判断用户手机号是否存在
        if(StringUtils.isBlank(phone)){
            return ServerResponse.createByErrorMessage("参数错误");
        }
        int resultCount1 = userMapper.checkPhone(phone);
        if(resultCount1 > 0) {
            log.warn("校验手机号：{}，手机号已经存在",phone);
            return ServerResponse.createByErrorMessage("手机号已经注册过");
        }
        // MD5 加密
        int resultCount2 = userMapper.insertUser(phone, Md5Util.md5EncodeUtf8(password));
        if(resultCount2 == 0) {
            log.warn("用户手机号{}注册异常", phone);
            return ServerResponse.createByErrorMessage("注册失败");
        } else {
            log.info("用户手机号{}注册成功", phone);
            return ServerResponse.createBySuccessMessage("注册成功");
        }
    }

}

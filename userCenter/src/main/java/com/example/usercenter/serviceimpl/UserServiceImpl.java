package com.example.usercenter.serviceimpl;

import com.example.usercenter.Service.user.UserService;
import com.example.usercenter.bean.pojo.User;
import com.example.usercenter.common.ServerResponse;
import com.example.usercenter.dao.UserMapper;
import com.example.usercenter.dataobject.OptCodeDo;
import com.example.usercenter.utils.Md5Util;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
    public ServerResponse<User> loginUseOtpCode(String phone, String otpCode) {
        // 校验otpCode
        OptCodeDo optCodeDo = userMapper.getOtpCodeByPhone(phone);
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//注意月和小时的格式为两个大写字母
        Date date = new Date();//获得当前时间
        Date lastTime = null;
        String updateTime = df.format(date);//将当前时间转换成特定格式的时间字符串，这样便可以插入到数据库中
        try {
            lastTime = df.parse(optCodeDo.getUpdateTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(lastTime != null) {
            long beginMillisecond = lastTime.getTime();
            long endMillisecond = date.getTime();
            if(endMillisecond - beginMillisecond < 1000*60*5 ) { // 5 分钟内获取到的otpCode比较
                // otpCode 比较
                if(otpCode.equals(optCodeDo.getOtpCode())){
                    // 比较成功 登陆成功
                    User user = userMapper.userLoginByPhone(phone);
                    user.setPassword(StringUtils.EMPTY);
                    log.info("用户手机号：{}，登陆成功", phone);
                    return ServerResponse.createBySuccess("登陆成功", user);
                }else{
                    return ServerResponse.createByError("验证码输入错误", null);
                }
            }else{
                return ServerResponse.createByError("请重新获取验证码", null);
            }
        }else{
            return ServerResponse.createByError("请重新获取验证码", null);
        }
    }

    @Override
    public ServerResponse<String> getOpt(String phone){
        Random random = new Random();
        int randomInt = random.nextInt(8999);
        randomInt += 1000;
        String otpCode = String.valueOf(randomInt);
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//注意月和小时的格式为两个大写字母
        java.util.Date date = new Date();//获得当前时间
        String updateTime = df.format(date);//将当前时间转换成特定格式的时间字符串，这样便可以插入到数据库中
        // 将 otp Code 放入数据表中
        userMapper.insertOtpCode(phone, otpCode, updateTime);
        return ServerResponse.createBySuccessMessage(otpCode);
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

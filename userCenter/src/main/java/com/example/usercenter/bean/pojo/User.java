package com.example.usercenter.bean.pojo;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
@Builder
public class User {
    /**
     * 用户表Id
     */
    private Integer id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户密码
     */
    @NotBlank(message = "用户密码不能为空")
    private String password;


    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 用户电话
     */
    @NotBlank(message = "用户手机号不能为空")
    private String phone;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 最后一次更新时间
     */
    private Date updateTime;

    /**
     * token
     */
    private String token;


}


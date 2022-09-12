package com.example.usercenter.common;

import lombok.Builder;
import lombok.Data;


public enum ResponseCode {
    /**
     * 请求成功状态码
     */
    SUCCESS(1, "SUCCESS"),
    /**
     * 请求失败状态码
     */
    ERROR(0, "ERROR");



    /**
     * 状态码
     */
    private final int code;

    /**
     * 描述
     */
    private final String desc;

    ResponseCode(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}

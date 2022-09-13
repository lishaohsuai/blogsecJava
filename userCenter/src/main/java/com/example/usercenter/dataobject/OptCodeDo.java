package com.example.usercenter.dataobject;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OptCodeDo {
    private String phone;
    private String otpCode;
    private String updateTime;
}

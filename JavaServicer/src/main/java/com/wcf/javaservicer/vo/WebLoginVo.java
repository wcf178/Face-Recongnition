package com.wcf.javaservicer.vo;

import lombok.Data;


/*用于登录的VO类  包括后端系统和微信小程序*/
@Data
public class WebLoginVo {
    private int userId;
    private String password;
}

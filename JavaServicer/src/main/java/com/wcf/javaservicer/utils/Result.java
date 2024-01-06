package com.wcf.javaservicer.utils;

import lombok.Data;

import java.io.Serializable;

@Data
public class Result implements Serializable {
    //响应前端是否成功的标识
    private Boolean flag;

    private long code;
    //响应信息
    private String message;
    //响应数据//
    private Object data;

    public Result() {
    }

    public Result(Boolean flag,String message ) {
        this.message = message;
        this.flag = flag;
    }

    public Result(boolean flag, String message, Object data) {
        this.flag = flag;
        this.message = message;
        this.data = data;
    }


    public static Result success(String message, Object data){
        return new Result(true,message,data);
    }
    public static Result success(String message){
        return new Result(true,message);
    }

    public static Result fail(String message){
        return new Result(false,message);
    }
}

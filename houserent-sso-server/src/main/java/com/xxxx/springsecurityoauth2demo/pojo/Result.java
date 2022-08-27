package com.xxxx.springsecurityoauth2demo.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * 返回前端实体类
 * @author qqq
 * @param <T>
 */
@Data
public class  Result<T> implements Serializable {
    private Integer code;
    private String message;
    private T data;

    public Result(Code code,  T data) {
        this.code = code.getCode();
        this.message = code.getMessage();
        this.data = data;
    }
    public Result(Code code) {//重载，可只传code
        this.code = code.getCode();
        this.message = code.getMessage();
    }
    public Result(String error){//重载，可只传message
        this.code=500;
        this.message = error;
    }

}


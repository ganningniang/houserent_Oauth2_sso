package com.xxxx.springsecurityoauth2demo.pojo;

/**
 * @author qqq
 */

public enum Code {

    /**
     *
     */
    SUCCESS(0,"成功！"),
    LOGIN_FAIL(10001,"用户名或密码错误"),
    RESULT_STRING_METHOD_VALUE_WRONG(10000,"resultString方法参数错误"),
    CODE_MISTAKE(100002,"后台代码错误"),
    CODE_REGIST_REPEAT(100003,"账户名已存在！");

    private Integer code;
    private String message;

    Code(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

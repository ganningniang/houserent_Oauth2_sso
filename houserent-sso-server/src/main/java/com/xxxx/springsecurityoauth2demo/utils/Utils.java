package com.xxxx.springsecurityoauth2demo.utils;

import com.alibaba.fastjson.JSON;
import com.xxxx.springsecurityoauth2demo.pojo.Code;
import com.xxxx.springsecurityoauth2demo.pojo.Result;

import java.util.HashMap;
import java.util.Map;

/**
 * 自定义工具包
 * @author qqq
 */
public class Utils {

    /**
     * 出错时掉调用，返回Code
     * @param code
     * @return
     */
    public static String FailResult(Code code){
        Result<Object> result = new Result<>(code);
        return toJsonString(result);
    }
    /**
     * 出错时掉调用，返回错误提示
     * @param message
     * @return
     */
    public static String FailResult(String message){
        Result<Object> result = new Result<>(message);
        return toJsonString(result);
    }

    /**
     * 成功时返回单个数据
     * @param object
     * @return
     */
    public static String result(Object object){
        Result<Object> result = new Result<>(Code.SUCCESS, object);
        String string = toJsonString(result);
        return string;

    }

    /**
     * 成功时 返回打包给前端的json字符串，返回多个数据，以下面格式来
     * @param args  返回数据，严格按照顺序：先数据名1，然后数据1，数据名2，数据2，。。。若无数据返回则不写
     * @return
     */
    public static  String resultMulti(Object...args){
        int len = args.length;
        HashMap<String, Object> map = new HashMap<String, Object>();
        Result result = null;
        if((len+2)%2!=0){
            result = new Result(Code.RESULT_STRING_METHOD_VALUE_WRONG,null);
        }
        if(len==0){
            result = new Result<Map>(Code.SUCCESS,null);
        }
        String name = null;
        Object o = null;
        for (int i =0;i<len;i++){

            if((i+2)%2==0 ){
                name = args[i].toString();
            }
            if ((i+2)%2!=0){
                o = args[i];
            }
            if(name!=null&&o!=null) {
                map.put(name, o);
                name=null;
                o=null;
            }
        }
        result = new Result<Map>(Code.SUCCESS,map);
        String string = toJsonString(result);
        return string;

    }
    /**
     * 对象转json
     * @param re
     * @return
     */
    public static String toJsonString(Result re){
        return JSON.toJSONString(re);
    }

}

package com.lzj.vo.resp;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;


/**
 * 返回数据类
 * @JsonInclude 保证序列化json的时候，如果是null的对象，key也会消失
 * @param <T>
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Result <T> implements Serializable {

    private static final long serialVersionUID = 7735505903525411467L;

    // 成功值,默认为1
    private static final int SUCCESS_CODE = 1;
    // 失败值,默认为0
    private static final int ERROR_CODE = 0;

    //状态码
    private int code;

    //信息
    private String msg;


    //返回数据
    private T data;

    public static <T> Result<T> ok(String msg){
        return new Result<>(SUCCESS_CODE,msg);
    }
    public static <T> Result<T> ok(T data){
        return new Result<>(SUCCESS_CODE,data);
    }
    public static <T> Result<T> ok(String msg, T data){
        return new Result<>(SUCCESS_CODE,msg,data);
    }

    private Result(int code){
        this.code=code;
    }

    private Result(int code,T data){
        this.code=code;
        this.data=data;
    }

    private Result(int code,String msg){
        this.code=code;
        this.msg=msg;
    }

    private Result(int code,String msg,T data){
        this.code=code;
        this.msg=msg;
        this.data=data;
    }

    public static <T> Result<T> ok(){
        return new Result<T>(SUCCESS_CODE,"success");
    }
    public static <T> Result<T> error(){
        return new Result<T>(ERROR_CODE,"error");
    }
    public static <T> Result<T> error(String msg){
        return new Result<T>(ERROR_CODE,msg);
    }
    public static <T> Result<T> error(int code, String msg){
        return new Result<T>(code,msg);
    }
    public static <T> Result<T> error(ResponseCode res){
        return new Result<T>(res.getCode(),res.getMessage());
    }

    public int getCode(){
        return code;
    }
    public String getMsg(){
        return msg;
    }
    public T getData(){
        return data;
    }
}

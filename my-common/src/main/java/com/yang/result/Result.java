package com.yang.result;

import lombok.Data;

import java.io.Serializable;

@Data
public class Result<T> implements Serializable {

    private Integer code; // 详情码
    private String msg; // 信息
    private T data; // 数据  先有泛型类，才有泛型变量

    public static <T> Result<T> success(T data) {
        Result<T> result=new Result<T>();
        result.code=1;
        result.data=data;
        return result;
    }

    public static <T> Result<T> success() { //  删除 更新 添加 不需要返回给前端的
        Result<T> result=new Result<>();
        result.code=1;
        return result;
    }

    public static <T> Result<T> error(String msg) {
        Result result=new Result<>();
        result.code=0;
        result.msg=msg;
        return result;
    }
}

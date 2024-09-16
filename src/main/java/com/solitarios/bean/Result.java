package com.solitarios.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//统一响应结果
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Result<T> {
    private Integer code;//业务状态码  0-成功  1-失败
    private String message;//提示信息
    private T data;//响应数据
    //快速返回操作成功响应结果(带响应数据)
    public static <T> Result<T> success(T data) {
        return new Result<>(0, "操作成功", data);
    }

    //快速返回操作成功响应结果
    public static <T> Result<T> success() {
        return new Result<>(0, "操作成功", null);
    }

    public static <T> Result<T> error(String message) {
        return new Result<>(1, message, null);
    }

    public static final int SUCCESS = 0;
    public static final int ERROR = 1;
}
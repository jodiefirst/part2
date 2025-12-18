package com.agri.platform.common;

import lombok.Data;

/**
 * 通用响应结果类
 */
@Data
public class Result<T> {
    // 响应码
    private Integer code;
    // 响应信息
    private String message;
    // 响应数据
    private T data;

    // 私有构造方法
    private Result() {}

    // 成功响应
    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.setCode(200);
        result.setMessage("success");
        result.setData(data);
        return result;
    }

    // 成功响应（无数据）
    public static <T> Result<T> success() {
        return success(null);
    }

    // 错误响应
    public static <T> Result<T> error(Integer code, String message) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setMessage(message);
        return result;
    }

    // 默认错误响应
    public static <T> Result<T> error(String message) {
        return error(500, message);
    }
}
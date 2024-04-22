package com.shooter.sapibackend.common;

import com.shooter.sapibackend.enums.ResultCodeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ClassName: Result
 * Package: com.shooter.sapibackend.common
 * Description:
 *
 * @Author:Shooter
 * @Create 2024/2/22 17:00
 * @Version 1.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Result<T> {
    private Integer code;//业务状态码  0-成功  1-失败
    private String message;//提示信息
    private T data;//响应数据

    public static <T> Result<T> build(T data) {
        Result<T> result = new Result<>();
        if (data != null) {
            result.setData(data);
        }
        return result;
    }

    public static <T> Result<T> build(T data, Integer code, String message) {
        Result<T> result = build(data);
        result.setCode(code);
        result.setMessage(message);
        return result;
    }

    public static <T> Result<T> build(T data, ResultCodeEnum resultCodeEnum) {
        Result<T> result = build(data);
        result.setCode(resultCodeEnum.getCode());
        result.setMessage(resultCodeEnum.getMessage());
        return result;
    }

    public static <T> Result<T> success(T data) {
        Result<T> result = build(data, ResultCodeEnum.SUCCESS);
        return result;
    }

    public static Result error(String message) {
        Result result = build(null, 1,message);
        return result;
    }
    public static Result error(ResultCodeEnum resultCodeEnum,String message) {
        Result result = build(null, resultCodeEnum.getCode(),message);
        return result;
    }

    public static Result error(int ResultCodeEnum, String message){
        Result result = build(null, ResultCodeEnum,message);
        return result;
    }

    public static <T> Result<T> success() {
        Result<T> result = build(null, ResultCodeEnum.SUCCESS);
        return result;
    }
}

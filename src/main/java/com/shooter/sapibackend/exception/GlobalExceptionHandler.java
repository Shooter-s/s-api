package com.shooter.sapibackend.exception;

import com.shooter.sapibackend.common.Result;
import com.shooter.sapibackend.enums.ResultCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * ClassName: GlobalExceptionHandler
 * Package: com.shooter.sapibackend.exception
 * Description:
 *
 * @Author:Shooter
 * @Create 2024/2/24 11:13
 * @Version 1.0
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public Result<?> businessExceptionHandler(BusinessException e) {
        log.error("BusinessException", e);
        return Result.error(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public Result<?> runtimeExceptionHandler(RuntimeException e) {
        log.error("RuntimeException", e);
        return Result.error(ResultCodeEnum.SYSTEM_ERROR, "系统错误");
    }
}

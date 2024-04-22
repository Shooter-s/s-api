package com.shooter.sapibackend.utils;

import com.shooter.sapibackend.enums.ResultCodeEnum;
import com.shooter.sapibackend.exception.BusinessException;

/**
 * ClassName: ThrowUtils
 * Package: com.shooter.sapibackend.utils
 * Description:
 *
 * @Author:Shooter
 * @Create 2024/2/22 19:53
 * @Version 1.0
 */
public class ThrowUtils {

    /**
     * 条件成立则抛异常
     *
     * @param condition
     * @param runtimeException
     */
    public static void throwIf(boolean condition, RuntimeException runtimeException) {
        if (condition) {
            throw runtimeException;
        }
    }

    /**
     * 条件成立则抛异常
     *
     * @param condition
     * @param ResultCodeEnum
     */
    public static void throwIf(boolean condition, ResultCodeEnum ResultCodeEnum) {
        throwIf(condition, new BusinessException(ResultCodeEnum));
    }

    /**
     * 条件成立则抛异常
     *
     * @param condition
     * @param ResultCodeEnum
     * @param message
     */
    public static void throwIf(boolean condition, ResultCodeEnum ResultCodeEnum, String message) {
        throwIf(condition, new BusinessException(ResultCodeEnum, message));
    }
}

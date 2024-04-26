package com.shooter.sapicommon.model.enums;

import cn.hutool.core.util.StrUtil;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ClassName: InterfaceInfoStatusEnum
 * Package: com.shooter.sapibackend.enums
 * Description:
 * @Author:Shooter
 * @Create 2024/4/24 16:56
 * @Version 1.0
 */
public enum InterfaceInfoStatusEnum {
    ONLINE("上线",1),
    OFFLINE("下线",0);

    private final String text;

    private final int value;

    InterfaceInfoStatusEnum(String text, int value) {
        this.text = text;
        this.value = value;
    }

    // 获取值列表
    public static List<Integer> getValues(){
        return Arrays.stream(values()).map(item -> item.value).collect(Collectors.toList());
    }

    //根据 text 获取枚举
    public static InterfaceInfoStatusEnum getEnumByValue(String text){
        if (StrUtil.isEmpty(text)){
            return null;
        }
        for (InterfaceInfoStatusEnum anEnum : values()) {
            if (anEnum.text.equals(text)){
                return anEnum;
            }
        }
        return null;
    }

    public String getText() {
        return text;
    }

    public int getValue() {
        return value;
    }
}

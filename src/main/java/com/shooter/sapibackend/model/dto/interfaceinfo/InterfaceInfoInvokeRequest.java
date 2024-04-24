package com.shooter.sapibackend.model.dto.interfaceinfo;

import lombok.Data;

import java.io.Serializable;

/**
 * ClassName: InterfaceInfoInvokeRequest
 * Package: com.shooter.sapibackend.model.dto.interfaceinfo
 * Description:
 * @Author:Shooter
 * @Create 2024/4/24 20:39
 * @Version 1.0
 */
@Data
public class InterfaceInfoInvokeRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 接口id
     */
    private Long id;
    /**
     * 用户传递过来的参数
     */
    private String userRequestParams;

}

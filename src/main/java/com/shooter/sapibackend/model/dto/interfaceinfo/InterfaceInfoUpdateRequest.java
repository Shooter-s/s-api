package com.shooter.sapibackend.model.dto.interfaceinfo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * ClassName: InterfaceInfoAddRequest
 * Package: com.shooter.sapibackend.model.dto.interfaceinfo
 * Description:
 * @Author:Shooter
 * @Create 2024/4/23 7:52
 * @Version 1.0
 */
@Data
public class InterfaceInfoUpdateRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键(在更新的时候，虽然不可以改id，但是更新数据的时候需要根据id知道哪一条数据)
     */
    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 描述
     */
    private String description;

    /**
     * 接口地址
     */
    private String url;

    /**
     * 请求参数
     */
    private String requestParams;

    /**
     * 请求头
     */
    private String requestHeader;

    /**
     * 响应头
     */
    private String responseHeader;

    /**
     * 接口状态（0-关闭，1-开启）
     */
    private Integer status;

    /**
     * 请求类型
     */
    private String method;

}

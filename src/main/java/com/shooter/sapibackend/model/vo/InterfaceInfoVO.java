package com.shooter.sapibackend.model.vo;

import com.shooter.sapicommon.model.entity.InterfaceInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * ClassName: InterfaceInfoVO
 * Package: com.shooter.sapibackend.model.vo
 * Description:
 * @Author:Shooter
 * @Create 2024/4/28 9:42
 * @Version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class InterfaceInfoVO extends InterfaceInfo {

    /**
     * 调用次数
     */
    private Integer totalNum;

    private static final long serialVersionUID = 1L;

}

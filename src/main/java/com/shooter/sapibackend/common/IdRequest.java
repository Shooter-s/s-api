package com.shooter.sapibackend.common;

import lombok.Data;

import java.io.Serializable;

/**
 * ClassName: IdRequest
 * Package: com.shooter.sapibackend.common
 * Description:
 * @Author:Shooter
 * @Create 2024/4/24 16:34
 * @Version 1.0
 */
@Data
public class IdRequest implements Serializable {
    /**
     * id
     */
    private Long id;
    private static final long serialVersionUID = 1L;

}

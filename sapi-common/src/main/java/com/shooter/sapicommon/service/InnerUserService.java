package com.shooter.sapicommon.service;

import com.shooter.sapicommon.model.entity.User;

/**
 * ClassName: InnerUserService
 * Package: com.shooter.sapicommon.service
 * Description:
 * @Author:Shooter
 * @Create 2024/4/26 14:39
 * @Version 1.0
 */
public interface InnerUserService {

    /**
     * 数据库中查是否已分配给用户秘钥
     */
    User getInvokeUser(String accessKey);


}

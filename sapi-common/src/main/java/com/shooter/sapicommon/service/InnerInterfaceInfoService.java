package com.shooter.sapicommon.service;

import com.shooter.sapicommon.model.entity.InterfaceInfo;

/**
 * ClassName: InnerInterfaceInfoService
 * Package: com.shooter.sapicommon.service
 * Description:
 * @Author:Shooter
 * @Create 2024/4/26 14:41
 * @Version 1.0
 */
public interface InnerInterfaceInfoService {

    /**
     * 从数据库中查询模拟接口是否存在（请求路径、请求方法、请求参数）
     */
    InterfaceInfo getInterfaceInfo(String path,String method);

}

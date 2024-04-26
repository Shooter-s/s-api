package com.shooter.sapicommon.service;

/**
 * ClassName: InnerUserInterfaceInfoService
 * Package: com.shooter.sapicommon.service
 * Description: 内部用户接口信息服务
 * @Author:Shooter
 * @Create 2024/4/26 14:32
 * @Version 1.0
 */
public interface InnerUserInterfaceInfoService {

    /**
     *调用统计接口
     * @param interfaceInfoId
     * @param userId
     * @return
     */
    boolean invokeCount(long interfaceInfoId,long userId);

    /**
     * 是否剩余接口调用次数
     * @param interfaceInfoId
     * @param userId
     * @return
     */
    boolean hasLeftNum(long interfaceInfoId,long userId);

}

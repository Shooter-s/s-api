package com.shooter.sapibackend.service.impl.inner;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.shooter.sapibackend.enums.ResultCodeEnum;
import com.shooter.sapibackend.exception.BusinessException;
import com.shooter.sapibackend.service.IInterfaceInfoService;
import com.shooter.sapicommon.model.entity.InterfaceInfo;
import com.shooter.sapicommon.service.InnerInterfaceInfoService;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * ClassName: InnerInterfaceInfoServiceImpl
 * Package: com.shooter.sapibackend.service.impl.inner
 * Description:
 * @Author:Shooter
 * @Create 2024/4/26 15:10
 * @Version 1.0
 */
@DubboService
public class InnerInterfaceInfoServiceImpl implements InnerInterfaceInfoService {

    @Resource
    private IInterfaceInfoService interfaceInfoService;
    @Override
    public InterfaceInfo getInterfaceInfo(String url, String method) {
        if (StringUtils.isAnyBlank(url,method)){
            throw new BusinessException(ResultCodeEnum.PARAMS_ERROR);
        }
        QueryWrapper<InterfaceInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("url",url);
        queryWrapper.eq("method", method);
        return interfaceInfoService.getOne(queryWrapper);
    }
}

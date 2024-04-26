package com.shooter.sapibackend.service.impl.inner;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.shooter.sapibackend.enums.ResultCodeEnum;
import com.shooter.sapibackend.exception.BusinessException;
import com.shooter.sapibackend.service.IUserService;
import com.shooter.sapicommon.model.entity.User;
import com.shooter.sapicommon.service.InnerUserService;
import jakarta.annotation.Resource;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * ClassName: InnerUserServiceImpl
 * Package: com.shooter.sapibackend.service.impl
 * Description:
 * @Author:Shooter
 * @Create 2024/4/26 14:58
 * @Version 1.0
 */
@DubboService
public class InnerUserServiceImpl implements InnerUserService {

    @Resource
    private IUserService userService;

    @Override
    public User getInvokeUser(String accessKey) {
        if (StrUtil.isEmpty(accessKey)){
            throw new BusinessException(ResultCodeEnum.PARAMS_ERROR);
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("accessKey", accessKey);
        return userService.getOne(queryWrapper);
    }
}

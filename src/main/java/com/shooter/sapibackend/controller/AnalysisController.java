package com.shooter.sapibackend.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.shooter.sapibackend.annotation.AuthCheck;
import com.shooter.sapibackend.common.Result;
import com.shooter.sapibackend.enums.ResultCodeEnum;
import com.shooter.sapibackend.exception.BusinessException;
import com.shooter.sapibackend.mapper.UserInterfaceInfoMapper;
import com.shooter.sapibackend.model.vo.InterfaceInfoVO;
import com.shooter.sapibackend.service.IInterfaceInfoService;
import com.shooter.sapicommon.model.entity.InterfaceInfo;
import com.shooter.sapicommon.model.entity.UserInterfaceInfo;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * ClassName: AnalysisController
 * Package: com.shooter.sapibackend.controller
 * Description:
 * @Author:Shooter
 * @Create 2024/4/28 9:52
 * @Version 1.0
 */
@RestController
@RequestMapping("/analysis")
@Slf4j
public class AnalysisController {

    @Resource
    private UserInterfaceInfoMapper userInterfaceInfoMapper;
    @Resource
    private IInterfaceInfoService interfaceInfoService;

    @GetMapping("/top/interface/invoke")
    @AuthCheck(mustRole = "admin")
    public Result<List<InterfaceInfoVO>> listTopInvokeInterfaceInfo(){

        // 前三名的： InterfaceInfoId，totalNum
        List<UserInterfaceInfo> userInterfaceInfos = userInterfaceInfoMapper.listTopInvokeInterfaceInfo(3);
        Map<Long, List<UserInterfaceInfo>> interfaceInfoIdObjMap = userInterfaceInfos.stream()
                .collect(Collectors.groupingBy(UserInterfaceInfo::getInterfaceInfoId));
        Set<Long> ids = interfaceInfoIdObjMap.keySet();
        String idStr = StrUtil.join(",", ids);
        // 根据interfaceId查出分别的接口信息
        QueryWrapper<InterfaceInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("id", ids);
        queryWrapper.last("order by field(id," + idStr + ")");
        List<InterfaceInfo> list = interfaceInfoService.list(queryWrapper);
        if (CollUtil.isEmpty(list)){
            throw new BusinessException(ResultCodeEnum.PARAMS_ERROR);
        }
        // 封装结果集 (List<InterfaceInfo> -> List<InterfaceInfoVO>)
        List<InterfaceInfoVO> interfaceInfoVOList = list.stream().map(interfaceInfo -> {
            InterfaceInfoVO interfaceInfoVO = new InterfaceInfoVO();
            BeanUtils.copyProperties(interfaceInfo, interfaceInfoVO);
            int totalNum = interfaceInfoIdObjMap.get(interfaceInfo.getId()).get(0).getTotalNum();
            interfaceInfoVO.setTotalNum(totalNum);
            return interfaceInfoVO;
        }).collect(Collectors.toList());
        return Result.success(interfaceInfoVOList);
    }

}

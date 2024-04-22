package com.shooter.sapibackend.controller;


import com.shooter.sapibackend.common.Result;
import com.shooter.sapibackend.enums.ResultCodeEnum;
import com.shooter.sapibackend.exception.BusinessException;
import com.shooter.sapibackend.model.dto.postthumb.PostThumbAddRequest;
import com.shooter.sapibackend.model.po.User;
import com.shooter.sapibackend.service.IPostThumbService;
import com.shooter.sapibackend.service.IUserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 帖子点赞 前端控制器
 * </p>
 *
 * @author shooter
 * @since 2024-03-20
 */
@RestController
@RequestMapping("/post-thumb")
@Slf4j
public class PostThumbController {


    @Resource
    private IPostThumbService postThumbService;

    @Resource
    private IUserService userService;

    /**
     * 点赞 / 取消点赞
     *
     * @param postThumbAddRequest
     * @param request
     * @return resultNum 本次点赞变化数
     */
    @PostMapping("/")
    public Result<Integer> doThumb(@RequestBody PostThumbAddRequest postThumbAddRequest,
                                   HttpServletRequest request) {
        if (postThumbAddRequest == null || postThumbAddRequest.getPostId() <= 0) {
            throw new BusinessException(ResultCodeEnum.PARAMS_ERROR);
        }
        // 登录才能点赞
        final User loginUser = userService.getLoginUser(request);
        long postId = postThumbAddRequest.getPostId();
        int result = postThumbService.doPostThumb(postId, loginUser);
        return Result.success(result);
    }


}

package com.shooter.sapibackend.service;

import com.shooter.sapibackend.model.po.PostThumb;
import com.baomidou.mybatisplus.extension.service.IService;
import com.shooter.sapicommon.model.entity.User;

/**
 * <p>
 * 帖子点赞 服务类
 * </p>
 *
 * @author shooter
 * @since 2024-03-20
 */
public interface IPostThumbService extends IService<PostThumb> {

    /**
     * 点赞
     *
     * @param postId
     * @param loginUser
     * @return
     */
    int doPostThumb(long postId, User loginUser);

    /**
     * 帖子点赞（内部服务）
     *
     * @param userId
     * @param postId
     * @return
     */
    int doPostThumbInner(long userId, long postId);
}

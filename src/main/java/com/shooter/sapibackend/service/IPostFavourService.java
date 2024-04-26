package com.shooter.sapibackend.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shooter.sapibackend.model.po.Post;
import com.shooter.sapibackend.model.po.PostFavour;
import com.baomidou.mybatisplus.extension.service.IService;
import com.shooter.sapicommon.model.entity.User;

/**
 * <p>
 * 帖子收藏 服务类
 * </p>
 *
 * @author shooter
 * @since 2024-03-20
 */
public interface IPostFavourService extends IService<PostFavour> {


    /**
     * 帖子收藏
     *
     * @param postId
     * @param loginUser
     * @return
     */
    int doPostFavour(long postId, User loginUser);

    /**
     * 分页获取用户收藏的帖子列表
     *
     * @param page
     * @param queryWrapper
     * @param favourUserId
     * @return
     */
    Page<Post> listFavourPostByPage(IPage<Post> page, Wrapper<Post> queryWrapper,
                                    long favourUserId);

    /**
     * 帖子收藏（内部服务）
     *
     * @param userId
     * @param postId
     * @return
     */
    int doPostFavourInner(long userId, long postId);

}

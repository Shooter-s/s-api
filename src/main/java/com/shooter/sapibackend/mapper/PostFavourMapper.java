package com.shooter.sapibackend.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shooter.sapibackend.model.po.Post;
import com.shooter.sapibackend.model.po.PostFavour;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 帖子收藏 Mapper 接口
 * </p>
 *
 * @author shooter
 * @since 2024-03-20
 */
public interface PostFavourMapper extends BaseMapper<PostFavour> {


    /**
     * 分页查询我收藏帖子列表
     *
     * @param page
     * @param queryWrapper
     * @param favourUserId
     * @return
     */
    Page<Post> listFavourPostByPage(IPage<Post> page, @Param(Constants.WRAPPER) Wrapper<Post> queryWrapper,
                                    long favourUserId);
}

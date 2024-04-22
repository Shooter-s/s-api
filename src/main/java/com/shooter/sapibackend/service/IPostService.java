package com.shooter.sapibackend.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shooter.sapibackend.model.dto.post.PostQueryRequest;
import com.shooter.sapibackend.model.po.Post;
import com.baomidou.mybatisplus.extension.service.IService;
import com.shooter.sapibackend.model.vo.PostVO;
import jakarta.servlet.http.HttpServletRequest;

/**
 * <p>
 * 帖子 服务类
 * </p>
 *
 * @author shooter
 * @since 2024-03-20
 */
public interface IPostService extends IService<Post> {


    /**
     * 校验
     *
     * @param post
     * @param add
     */
    void validPost(Post post, boolean add);

    /**
     * 获取查询条件
     *
     * @param postQueryRequest
     * @return
     */
    QueryWrapper<Post> getQueryWrapper(PostQueryRequest postQueryRequest);

    /**
     * 获取帖子封装
     *
     * @param post
     * @param request
     * @return
     */
    PostVO getPostVO(Post post, HttpServletRequest request);

    /**
     * 分页获取帖子封装
     *
     * @param postPage
     * @param request
     * @return
     */
    Page<PostVO> getPostVOPage(Page<Post> postPage, HttpServletRequest request);

    /**
     * 分页查询帖子
     * @param postQueryRequest
     * @param request
     * @return
     */
    Page<PostVO> listPostVOByPage(PostQueryRequest postQueryRequest, HttpServletRequest request);

}

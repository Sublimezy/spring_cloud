package com.ruoyi.comments.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.core.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.comments.mapper.CommentsMapper;
import com.ruoyi.comments.entity.dos.Comments;
import com.ruoyi.comments.service.ICommentsService;

/**
 * 用户评价Service业务层处理
 * 
 * @author zhangyan
 * @date 2024-06-08
 */
@Service
public class CommentsServiceImpl extends ServiceImpl<CommentsMapper, Comments> implements ICommentsService
{
    @Autowired
    private CommentsMapper commentsMapper;

    /**
     * 查询用户评价
     * 
     * @param id 用户评价主键
     * @return 用户评价
     */
    @Override
    public Comments selectCommentsById(String id)
    {
        return commentsMapper.selectCommentsById(id);
    }

    /**
     * 查询用户评价列表
     * 
     * @param comments 用户评价
     * @return 用户评价
     */
    @Override
    public List<Comments> selectCommentsList(Comments comments)
    {
        return commentsMapper.selectCommentsList(comments);
    }

    /**
     * 新增用户评价
     * 
     * @param comments 用户评价
     * @return 结果
     */
    @Override
    public int insertComments(Comments comments)
    {
        comments.setCreateTime(DateUtils.getNowDate());
        return commentsMapper.insertComments(comments);
    }

    /**
     * 修改用户评价
     * 
     * @param comments 用户评价
     * @return 结果
     */
    @Override
    public int updateComments(Comments comments)
    {
        comments.setUpdateTime(DateUtils.getNowDate());
        return commentsMapper.updateComments(comments);
    }

    /**
     * 批量删除用户评价
     * 
     * @param ids 需要删除的用户评价主键
     * @return 结果
     */
    @Override
    public int deleteCommentsByIds(String[] ids)
    {
        return commentsMapper.deleteCommentsByIds(ids);
    }

    /**
     * 删除用户评价信息
     * 
     * @param id 用户评价主键
     * @return 结果
     */
    @Override
    public int deleteCommentsById(String id)
    {
        return commentsMapper.deleteCommentsById(id);
    }
}

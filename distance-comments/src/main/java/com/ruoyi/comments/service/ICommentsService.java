package com.ruoyi.comments.service;

import java.util.List;
import com.ruoyi.comments.entity.dos.Comments;

/**
 * 用户评价Service接口
 * 
 * @author zhangyan
 * @date 2024-06-08
 */
public interface ICommentsService 
{
    /**
     * 查询用户评价
     * 
     * @param id 用户评价主键
     * @return 用户评价
     */
    public Comments selectCommentsById(String id);

    /**
     * 查询用户评价列表
     * 
     * @param comments 用户评价
     * @return 用户评价集合
     */
    public List<Comments> selectCommentsList(Comments comments);

    /**
     * 新增用户评价
     * 
     * @param comments 用户评价
     * @return 结果
     */
    public int insertComments(Comments comments);

    /**
     * 修改用户评价
     * 
     * @param comments 用户评价
     * @return 结果
     */
    public int updateComments(Comments comments);

    /**
     * 批量删除用户评价
     * 
     * @param ids 需要删除的用户评价主键集合
     * @return 结果
     */
    public int deleteCommentsByIds(String[] ids);

    /**
     * 删除用户评价信息
     * 
     * @param id 用户评价主键
     * @return 结果
     */
    public int deleteCommentsById(String id);
}

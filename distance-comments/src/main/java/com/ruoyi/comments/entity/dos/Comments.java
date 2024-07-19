package com.ruoyi.comments.entity.dos;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 用户评价对象 comments
 * 
 * @author zhangyan
 * @date 2024-06-08
 */
public class Comments extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private String id;

    /** 用户id */
    @Excel(name = "用户id")
    private String userId;

    /** 商店id */
    @Excel(name = "商店id")
    private String blogId;

    /** 关联的1级评论id，如果是一级评论，则值为0 */
    @Excel(name = "关联的1级评论id，如果是一级评论，则值为0")
    private String parentId;

    /** 回复的评论id */
    @Excel(name = "回复的评论id")
    private String answerId;

    /** 回复的内容 */
    @Excel(name = "回复的内容")
    private String content;

    /** 点赞数 */
    @Excel(name = "点赞数")
    private String liked;

    /** 状态，0：正常，1：被举报，2：禁止查看 */
    @Excel(name = "状态，0：正常，1：被举报，2：禁止查看")
    private String status;

    public void setId(String id) 
    {
        this.id = id;
    }

    public String getId() 
    {
        return id;
    }
    public void setUserId(String userId) 
    {
        this.userId = userId;
    }

    public String getUserId() 
    {
        return userId;
    }
    public void setBlogId(String blogId) 
    {
        this.blogId = blogId;
    }

    public String getBlogId() 
    {
        return blogId;
    }
    public void setParentId(String parentId) 
    {
        this.parentId = parentId;
    }

    public String getParentId() 
    {
        return parentId;
    }
    public void setAnswerId(String answerId) 
    {
        this.answerId = answerId;
    }

    public String getAnswerId() 
    {
        return answerId;
    }
    public void setContent(String content) 
    {
        this.content = content;
    }

    public String getContent() 
    {
        return content;
    }
    public void setLiked(String liked) 
    {
        this.liked = liked;
    }

    public String getLiked() 
    {
        return liked;
    }
    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("userId", getUserId())
            .append("blogId", getBlogId())
            .append("parentId", getParentId())
            .append("answerId", getAnswerId())
            .append("content", getContent())
            .append("liked", getLiked())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}

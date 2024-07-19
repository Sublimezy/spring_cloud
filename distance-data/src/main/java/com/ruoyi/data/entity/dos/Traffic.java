package com.ruoyi.data.entity.dos;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 交通信息对象 traffic
 * 
 * @author zhangyan
 * @date 2024-06-08
 */
@Data
@TableName("traffic")
public class Traffic extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 交通ID */
    @TableId(type = IdType.AUTO)
    private Long id;

    /** 交通方式，如公交、出租车、租车等 */
    @Excel(name = "交通方式，如公交、出租车、租车等")
    private String mode;

    /** 交通路线描述 */
    @Excel(name = "交通路线描述")
    private String route;

    /** 价格 */
    @Excel(name = "价格")
    private Long price;

    /** 出发时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "出发时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date departureTime;

    /** 到达时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "到达时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date arrivalTime;

    /** 记录创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "记录创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createdAt;

    /** 记录更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "记录更新时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date updatedAt;

    /** 司机id */
    @Excel(name = "司机id")
    private Long userId;

    /** 司机名称 */
    @Excel(name = "司机名称")
    private String userName;

    /** 司机图片 */
    @Excel(name = "司机图片")
    private String userImages;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setMode(String mode) 
    {
        this.mode = mode;
    }

    public String getMode() 
    {
        return mode;
    }
    public void setRoute(String route) 
    {
        this.route = route;
    }

    public String getRoute() 
    {
        return route;
    }
    public void setPrice(Long price) 
    {
        this.price = price;
    }

    public Long getPrice() 
    {
        return price;
    }
    public void setDepartureTime(Date departureTime) 
    {
        this.departureTime = departureTime;
    }

    public Date getDepartureTime() 
    {
        return departureTime;
    }
    public void setArrivalTime(Date arrivalTime) 
    {
        this.arrivalTime = arrivalTime;
    }

    public Date getArrivalTime() 
    {
        return arrivalTime;
    }
    public void setCreatedAt(Date createdAt) 
    {
        this.createdAt = createdAt;
    }

    public Date getCreatedAt() 
    {
        return createdAt;
    }
    public void setUpdatedAt(Date updatedAt) 
    {
        this.updatedAt = updatedAt;
    }

    public Date getUpdatedAt() 
    {
        return updatedAt;
    }
    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }
    public void setUserName(String userName) 
    {
        this.userName = userName;
    }

    public String getUserName() 
    {
        return userName;
    }
    public void setUserImages(String userImages) 
    {
        this.userImages = userImages;
    }

    public String getUserImages() 
    {
        return userImages;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("mode", getMode())
            .append("route", getRoute())
            .append("price", getPrice())
            .append("departureTime", getDepartureTime())
            .append("arrivalTime", getArrivalTime())
            .append("createdAt", getCreatedAt())
            .append("updatedAt", getUpdatedAt())
            .append("userId", getUserId())
            .append("userName", getUserName())
            .append("userImages", getUserImages())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}

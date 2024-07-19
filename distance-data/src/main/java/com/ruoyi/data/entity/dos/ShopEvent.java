package com.ruoyi.data.entity.dos;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 店铺活动对象 shop_event
 *
 * @author zhangyan
 * @date 2024-06-10
 */
@Data
@NoArgsConstructor
@TableName("shop_event")
public class ShopEvent extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 活动图片
     */
    @Excel(name = "活动图片")
    private String eventLogo;

    /**
     * 店铺id
     */
    @Excel(name = "店铺id")
    private Long shopId;

    /**
     * 店铺名
     */
    @Excel(name = "店铺名")
    private String shopName;

    /**
     * 活动名称
     */
    @Excel(name = "活动名称")
    private String eventName;

    /**
     * 活动描述
     */
    @Excel(name = "活动描述")
    private String description;

    /**
     * 开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "开始时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date startDate;

    /**
     * 结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "结束时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date endDate;

    /**
     * 是否折扣
     */
    @Excel(name = "是否折扣")
    private Long isDiscount;

    /**
     * 折扣率
     */
    @Excel(name = "折扣率")
    private BigDecimal discountRate;


    /**
     * 金额
     */
    @Excel(name = "金额")
    private String amount;

    /**
     * 活动数量
     */
    @Excel(name = "活动数量")
    private Long activityNum;

    /**
     * 浏览量
     */
    @Excel(name = "浏览量")
    private Long pageView;

    /**
     * 点赞数
     */
    @Excel(name = "点赞数")
    private Long likes;

    @TableField(exist = false)
    private Integer originAmount;

    @TableField(exist = false)
    private Integer score;

    public ShopEvent(String eventLogo, String eventName, Long isDiscount, String amount, Integer originAmount, Integer score) {
        this.eventLogo = eventLogo;
        this.eventName = eventName;
        this.isDiscount = isDiscount;
        this.amount = amount;
        this.originAmount = originAmount;
        this.score = score;
    }

    public ShopEvent(String eventLogo, String eventName, Integer score) {
        this.eventLogo = eventLogo;
        this.eventName = eventName;
        this.score = score;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setEventLogo(String eventLogo) {
        this.eventLogo = eventLogo;
    }

    public String getEventLogo() {
        return eventLogo;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopName() {
        return shopName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventName() {
        return eventName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setIsDiscount(Long isDiscount) {
        this.isDiscount = isDiscount;
    }

    public Long getIsDiscount() {
        return isDiscount;
    }

    public void setDiscountRate(BigDecimal discountRate) {
        this.discountRate = discountRate;
    }

    public BigDecimal getDiscountRate() {
        return discountRate;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getAmount() {
        return amount;
    }

    public void setActivityNum(Long activityNum) {
        this.activityNum = activityNum;
    }

    public Long getActivityNum() {
        return activityNum;
    }

    public void setPageView(Long pageView) {
        this.pageView = pageView;
    }

    public Long getPageView() {
        return pageView;
    }

    public void setLikes(Long likes) {
        this.likes = likes;
    }

    public Long getLikes() {
        return likes;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("eventLogo", getEventLogo())
                .append("shopId", getShopId())
                .append("shopName", getShopName())
                .append("eventName", getEventName())
                .append("description", getDescription())
                .append("startDate", getStartDate())
                .append("endDate", getEndDate())
                .append("isDiscount", getIsDiscount())
                .append("discountRate", getDiscountRate())
                .append("remark", getRemark())
                .append("createBy", getCreateBy())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("amount", getAmount())
                .append("activityNum", getActivityNum())
                .append("pageView", getPageView())
                .append("likes", getLikes())
                .toString();
    }
}

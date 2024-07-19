package com.ruoyi.order.entity.dos;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 用户订单对象 user_order
 *
 * @author zhangyan
 * @date 2024-06-15
 */
@Data
@TableName("user_order")
public class UserOrder extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 订单ID */
    private Long id;

    /** 订单编号 */
    @Excel(name = "订单编号")
    private String orderNumber;

    /** 店铺ID */
    @Excel(name = "店铺ID")
    private Long shopId;

    /** 店铺名称 */
    @Excel(name = "店铺名称")
    private String name;

    /** 店铺活动ID */
    @Excel(name = "店铺活动ID")
    private Long shopEventId;

    /** 活动名称 */
    @Excel(name = "活动名称")
    private String eventName;

    /** 用户ID */
    @Excel(name = "用户ID")
    private Long userId;

    /** 用户账号 */
    @Excel(name = "用户账号")
    private String userName;

    /** 单价 */
    @Excel(name = "单价")
    private Long amount;

    /** 订单总金额 */
    @Excel(name = "订单总金额")
    private Long totalAmount;

    /** 订单项数量 */
    @Excel(name = "订单项数量")
    private Long itemCount;

    /** 优惠金额 */
    @Excel(name = "优惠金额")
    private Long discountAmount;

    /** 优惠券代码 */
    @Excel(name = "优惠券代码")
    private String couponCode;

    /** 运费 */
    @Excel(name = "运费")
    private Long shippingFee;

    /** 订单状态 */
    @Excel(name = "订单状态")
    private String status;

    /** 支付方式 */
    @Excel(name = "支付方式")
    private String paymentMethod;

    /** 支付时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "支付时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date paymentTime;

    /** 发货时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "发货时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date shippingTime;

    /** 配送地址 */
    @Excel(name = "配送地址")
    private String shippingAddress;

    /** 用户备注 */
    @Excel(name = "用户备注")
    private String userNote;

    /** 订单类型 */
    @Excel(name = "订单类型")
    private Long orderType;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setOrderNumber(String orderNumber)
    {
        this.orderNumber = orderNumber;
    }

    public String getOrderNumber()
    {
        return orderNumber;
    }
    public void setShopId(Long shopId)
    {
        this.shopId = shopId;
    }

    public Long getShopId()
    {
        return shopId;
    }
    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }
    public void setShopEventId(Long shopEventId)
    {
        this.shopEventId = shopEventId;
    }

    public Long getShopEventId()
    {
        return shopEventId;
    }
    public void setEventName(String eventName)
    {
        this.eventName = eventName;
    }

    public String getEventName()
    {
        return eventName;
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
    public void setAmount(Long amount)
    {
        this.amount = amount;
    }

    public Long getAmount()
    {
        return amount;
    }
    public void setTotalAmount(Long totalAmount)
    {
        this.totalAmount = totalAmount;
    }

    public Long getTotalAmount()
    {
        return totalAmount;
    }
    public void setItemCount(Long itemCount)
    {
        this.itemCount = itemCount;
    }

    public Long getItemCount()
    {
        return itemCount;
    }
    public void setDiscountAmount(Long discountAmount)
    {
        this.discountAmount = discountAmount;
    }

    public Long getDiscountAmount()
    {
        return discountAmount;
    }
    public void setCouponCode(String couponCode)
    {
        this.couponCode = couponCode;
    }

    public String getCouponCode()
    {
        return couponCode;
    }
    public void setShippingFee(Long shippingFee)
    {
        this.shippingFee = shippingFee;
    }

    public Long getShippingFee()
    {
        return shippingFee;
    }
    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getStatus()
    {
        return status;
    }
    public void setPaymentMethod(String paymentMethod)
    {
        this.paymentMethod = paymentMethod;
    }

    public String getPaymentMethod()
    {
        return paymentMethod;
    }
    public void setPaymentTime(Date paymentTime)
    {
        this.paymentTime = paymentTime;
    }

    public Date getPaymentTime()
    {
        return paymentTime;
    }
    public void setShippingTime(Date shippingTime)
    {
        this.shippingTime = shippingTime;
    }

    public Date getShippingTime()
    {
        return shippingTime;
    }
    public void setShippingAddress(String shippingAddress)
    {
        this.shippingAddress = shippingAddress;
    }

    public String getShippingAddress()
    {
        return shippingAddress;
    }
    public void setUserNote(String userNote)
    {
        this.userNote = userNote;
    }

    public String getUserNote()
    {
        return userNote;
    }
    public void setOrderType(Long orderType)
    {
        this.orderType = orderType;
    }

    public Long getOrderType()
    {
        return orderType;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("orderNumber", getOrderNumber())
                .append("shopId", getShopId())
                .append("name", getName())
                .append("shopEventId", getShopEventId())
                .append("eventName", getEventName())
                .append("userId", getUserId())
                .append("userName", getUserName())
                .append("amount", getAmount())
                .append("totalAmount", getTotalAmount())
                .append("itemCount", getItemCount())
                .append("discountAmount", getDiscountAmount())
                .append("couponCode", getCouponCode())
                .append("shippingFee", getShippingFee())
                .append("status", getStatus())
                .append("paymentMethod", getPaymentMethod())
                .append("paymentTime", getPaymentTime())
                .append("shippingTime", getShippingTime())
                .append("shippingAddress", getShippingAddress())
                .append("userNote", getUserNote())
                .append("orderType", getOrderType())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("remark", getRemark())
                .toString();
    }
}

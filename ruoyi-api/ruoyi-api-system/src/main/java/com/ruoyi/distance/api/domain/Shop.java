package com.ruoyi.distance.api.domain;


import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;
import lombok.Data;

/**
 * 店铺对象 shop
 * 
 * @author zhangyan
 * @date 2024-06-08
 */
@Data
public class Shop extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 用户id */
    @Excel(name = "用户id")
    private Long userId;

    /** 经营人 */
    @Excel(name = "经营人")
    private String userName;

    /** 商铺名称 */
    @Excel(name = "商铺名称")
    private String name;

    /** 商铺类型 */
    @Excel(name = "商铺类型")
    private String type;

    /** 商铺图片 */
    @Excel(name = "商铺图片")
    private String images;

    /** 商圈 */
    @Excel(name = "商圈")
    private String area;

    /** 地址 */
    @Excel(name = "地址")
    private String address;

    /** 经度 */
    @Excel(name = "经度")
    private String x;

    /** 维度 */
    @Excel(name = "维度")
    private String y;

    /** 均价，取整数 */
    @Excel(name = "均价，取整数")
    private String avgPrice;

    /** 销量 */
    @Excel(name = "销量")
    private Integer sold;

    /** 评论数量 */
    @Excel(name = "评论数量")
    private Integer comments;

    /** 评分 */
    @Excel(name = "评分")
    private Integer score;

    /** 营业时间，例如 10:00-22:00 */
    @Excel(name = "营业时间，例如 10:00-22:00")
    private String openHours;

}

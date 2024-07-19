package com.ruoyi.data.entity.dos;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 店铺类型对象 shop_type
 *
 * @author zhangyan
 * @date 2024-06-08
 */
@Data
@TableName("shop_type")
public class ShopType extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    @TableId(type = IdType.AUTO)
    private String id;

    /** 类型名称 */
    @Excel(name = "类型名称")
    private String name;

    /** 图标 */
    @Excel(name = "图标")
    private String icon;

    /** 顺序 */
    @Excel(name = "顺序")
    private String sort;

    public void setId(String id)
    {
        this.id = id;
    }

    public String getId()
    {
        return id;
    }
    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }
    public void setIcon(String icon)
    {
        this.icon = icon;
    }

    public String getIcon()
    {
        return icon;
    }
    public void setSort(String sort)
    {
        this.sort = sort;
    }

    public String getSort()
    {
        return sort;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("name", getName())
                .append("icon", getIcon())
                .append("sort", getSort())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("remark", getRemark())
                .toString();
    }
}

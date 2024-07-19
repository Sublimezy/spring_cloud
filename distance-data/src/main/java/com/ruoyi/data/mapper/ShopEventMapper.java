package com.ruoyi.data.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.data.entity.dos.ShopEvent;

import java.util.List;

/**
 * 店铺活动Mapper接口
 *
 * @author zhangyan
 * @date 2024-06-10
 */
public interface ShopEventMapper  extends BaseMapper<ShopEvent>
{
    /**
     * 查询店铺活动
     *
     * @param id 店铺活动主键
     * @return 店铺活动
     */
    public ShopEvent selectShopEventById(Long id);

    /**
     * 查询店铺活动列表
     *
     * @param shopEvent 店铺活动
     * @return 店铺活动集合
     */
    public List<ShopEvent> selectShopEventList(ShopEvent shopEvent);

    /**
     * 新增店铺活动
     *
     * @param shopEvent 店铺活动
     * @return 结果
     */
    public int insertShopEvent(ShopEvent shopEvent);

    /**
     * 修改店铺活动
     *
     * @param shopEvent 店铺活动
     * @return 结果
     */
    public int updateShopEvent(ShopEvent shopEvent);

    /**
     * 删除店铺活动
     *
     * @param id 店铺活动主键
     * @return 结果
     */
    public int deleteShopEventById(Long id);

    /**
     * 批量删除店铺活动
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteShopEventByIds(Long[] ids);
}

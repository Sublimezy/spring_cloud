package com.ruoyi.data.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.data.entity.dos.ShopEvent;


/**
 * 店铺活动Service接口
 *
 * @author zhangyan
 * @date 2024-06-10
 */
public interface IShopEventService extends IService<ShopEvent>
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
    public int insertShopEvent(Long id,ShopEvent shopEvent);

    /**
     * 修改店铺活动
     *
     * @param shopEvent 店铺活动
     * @return 结果
     */
    public int updateShopEvent(ShopEvent shopEvent);

    /**
     * 批量删除店铺活动
     *
     * @param ids 需要删除的店铺活动主键集合
     * @return 结果
     */
    public int deleteShopEventByIds(Long[] ids);

    /**
     * 删除店铺活动信息
     *
     * @param id 店铺活动主键
     * @return 结果
     */
    public int deleteShopEventById(Long id);


}

package com.ruoyi.data.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.data.entity.dos.Shop;
import com.ruoyi.data.entity.dos.ShopEvent;
import com.ruoyi.data.mapper.ShopEventMapper;
import com.ruoyi.data.service.IShopEventService;
import com.ruoyi.data.service.IShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 店铺活动Service业务层处理
 *
 * @author zhangyan
 * @date 2024-06-10
 */
@Service
public class ShopEventServiceImpl extends ServiceImpl<ShopEventMapper, ShopEvent> implements IShopEventService {


    @Autowired
    private ShopEventMapper shopEventMapper;



    @Autowired
    private IShopService shopService;
    /**
     * 查询店铺活动
     *
     * @param id 店铺活动主键
     * @return 店铺活动
     */
    @Override
    public ShopEvent selectShopEventById(Long id) {
        return shopEventMapper.selectShopEventById(id);
    }

    /**
     * 查询店铺活动列表
     *
     * @param shopEvent 店铺活动
     * @return 店铺活动
     */
    @Override
    public List<ShopEvent> selectShopEventList(ShopEvent shopEvent) {
        return shopEventMapper.selectShopEventList(shopEvent);
    }

    /**
     * 新增店铺活动
     *
     * @param shopEvent 店铺活动
     * @return 结果
     */
    @Override
    public int insertShopEvent(Long id,ShopEvent shopEvent) {

        //基于远程调用
        /*System.out.println(id);
        R<?> registerResult = remoteDataService.getInfoByUserId(id, SecurityConstants.INNER);
        com.ruoyi.distance.api.domain.Shop shop = (com.ruoyi.distance.api.domain.Shop) registerResult.getData();*/


        Shop shop = shopService.getInfoByUserId(id);
        shopEvent.setShopId(shop.getId());
        shopEvent.setShopName(shop.getName());
        return shopEventMapper.insertShopEvent(shopEvent);
    }

    /**
     * 修改店铺活动
     *
     * @param shopEvent 店铺活动
     * @return 结果
     */
    @Override
    public int updateShopEvent(ShopEvent shopEvent) {
        shopEvent.setUpdateTime(DateUtils.getNowDate());
        return shopEventMapper.updateShopEvent(shopEvent);
    }

    /**
     * 批量删除店铺活动
     *
     * @param ids 需要删除的店铺活动主键
     * @return 结果
     */
    @Override
    public int deleteShopEventByIds(Long[] ids) {
        return shopEventMapper.deleteShopEventByIds(ids);
    }

    /**
     * 删除店铺活动信息
     *
     * @param id 店铺活动主键
     * @return 结果
     */
    @Override
    public int deleteShopEventById(Long id) {
        return shopEventMapper.deleteShopEventById(id);
    }

}

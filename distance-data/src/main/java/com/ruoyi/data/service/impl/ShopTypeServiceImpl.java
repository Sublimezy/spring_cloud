package com.ruoyi.data.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.rocketmq.channel.DolphinSource;
import com.ruoyi.data.entity.dos.ShopType;
import com.ruoyi.data.mapper.ShopTypeMapper;
import com.ruoyi.data.service.IShopTypeService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;


/**
 * 店铺类型Service业务层处理
 *
 * @author zhangyan
 * @date 2024-06-08
 */
@Service
@AllArgsConstructor
public class ShopTypeServiceImpl extends ServiceImpl<ShopTypeMapper, ShopType> implements IShopTypeService {
    @Autowired
    private ShopTypeMapper shopTypeMapper;



    /**
     * 查询店铺类型
     *
     * @param id 店铺类型主键
     * @return 店铺类型
     */
    @Override
    public ShopType selectShopTypeById(String id) {
        return shopTypeMapper.selectShopTypeById(id);
    }

    /**
     * 查询店铺类型列表
     *
     * @param shopType 店铺类型
     * @return 店铺类型
     */




    @Override
    public List<ShopType> selectShopTypeList(ShopType shopType) {
        System.out.println("发送消息");

        return shopTypeMapper.selectShopTypeList(shopType);
    }

    /**
     * 新增店铺类型
     *
     * @param shopType 店铺类型
     * @return 结果
     */
    @Override
    public int insertShopType(ShopType shopType) {
        shopType.setCreateTime(DateUtils.getNowDate());
        return shopTypeMapper.insertShopType(shopType);
    }

    /**
     * 修改店铺类型
     *
     * @param shopType 店铺类型
     * @return 结果
     */
    @Override
    public int updateShopType(ShopType shopType) {
        shopType.setUpdateTime(DateUtils.getNowDate());
        return shopTypeMapper.updateShopType(shopType);
    }

    /**
     * 批量删除店铺类型
     *
     * @param ids 需要删除的店铺类型主键
     * @return 结果
     */
    @Override
    public int deleteShopTypeByIds(String[] ids) {
        return shopTypeMapper.deleteShopTypeByIds(ids);
    }

    /**
     * 删除店铺类型信息
     *
     * @param id 店铺类型主键
     * @return 结果
     */
    @Override
    public int deleteShopTypeById(String id) {
        return shopTypeMapper.deleteShopTypeById(id);
    }


}

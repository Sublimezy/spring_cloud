package com.ruoyi.data.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.data.entity.dos.ShopType;

import java.util.List;


/**
 * 店铺类型Service接口
 *
 * @author zhangyan
 * @date 2024-06-08
 */
public interface IShopTypeService extends IService<ShopType>
{
    /**
     * 查询店铺类型
     *
     * @param id 店铺类型主键
     * @return 店铺类型
     */
    public ShopType selectShopTypeById(String id);

    /**
     * 查询店铺类型列表
     *
     * @param shopType 店铺类型
     * @return 店铺类型集合
     */
    public List<ShopType> selectShopTypeList(ShopType shopType);

    /**
     * 新增店铺类型
     *
     * @param shopType 店铺类型
     * @return 结果
     */
    public int insertShopType(ShopType shopType);

    /**
     * 修改店铺类型
     *
     * @param shopType 店铺类型
     * @return 结果
     */
    public int updateShopType(ShopType shopType);

    /**
     * 批量删除店铺类型
     *
     * @param ids 需要删除的店铺类型主键集合
     * @return 结果
     */
    public int deleteShopTypeByIds(String[] ids);

    /**
     * 删除店铺类型信息
     *
     * @param id 店铺类型主键
     * @return 结果
     */
    public int deleteShopTypeById(String id);

}

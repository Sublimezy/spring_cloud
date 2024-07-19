package com.ruoyi.data.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.data.entity.dos.Shop;

import java.util.List;

/**
 * 店铺Service接口
 * 
 * @author zhangyan
 * @date 2024-06-08
 */
public interface IShopService  extends IService<Shop>
{
    /**
     * 查询店铺
     * 
     * @param id 店铺主键
     * @return 店铺
     */
    public Shop selectShopById(String id);

    /**
     * 查询店铺列表
     * 
     * @param shop 店铺
     * @return 店铺集合
     */
    public List<Shop> selectShopList(Shop shop);

    /**
     * 新增店铺
     * 
     * @param shop 店铺
     * @return 结果
     */
    public int insertShop(Shop shop);

    /**
     * 修改店铺
     * 
     * @param shop 店铺
     * @return 结果
     */
    public int updateShop(Shop shop);

    /**
     * 批量删除店铺
     * 
     * @param ids 需要删除的店铺主键集合
     * @return 结果
     */
    public int deleteShopByIds(String[] ids);

    /**
     * 删除店铺信息
     * 
     * @param id 店铺主键
     * @return 结果
     */
    public int deleteShopById(String id);

    public Shop getInfoByUserId(Long id);

    public Boolean loadShopData();

    TableDataInfo queryShopList(Integer typeId, Integer pageNum, Integer pageSize, Double x, Double y, Double distance);

    TableDataInfo selectIndexList();
}

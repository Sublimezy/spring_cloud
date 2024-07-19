package com.ruoyi.data.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.data.entity.dos.Traffic;

/**
 * 交通信息Service接口
 * 
 * @author zhangyan
 * @date 2024-06-08
 */
public interface ITrafficService  extends IService<Traffic>
{
    /**
     * 查询交通信息
     * 
     * @param id 交通信息主键
     * @return 交通信息
     */
    public Traffic selectTrafficById(Long id);

    /**
     * 查询交通信息列表
     * 
     * @param traffic 交通信息
     * @return 交通信息集合
     */
    public List<Traffic> selectTrafficList(Traffic traffic);

    /**
     * 新增交通信息
     * 
     * @param traffic 交通信息
     * @return 结果
     */
    public int insertTraffic(Traffic traffic);

    /**
     * 修改交通信息
     * 
     * @param traffic 交通信息
     * @return 结果
     */
    public int updateTraffic(Traffic traffic);

    /**
     * 批量删除交通信息
     * 
     * @param ids 需要删除的交通信息主键集合
     * @return 结果
     */
    public int deleteTrafficByIds(Long[] ids);

    /**
     * 删除交通信息信息
     * 
     * @param id 交通信息主键
     * @return 结果
     */
    public int deleteTrafficById(Long id);
}

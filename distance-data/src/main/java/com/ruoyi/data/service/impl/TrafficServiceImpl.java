package com.ruoyi.data.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.core.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.data.mapper.TrafficMapper;
import com.ruoyi.data.entity.dos.Traffic;
import com.ruoyi.data.service.ITrafficService;

/**
 * 交通信息Service业务层处理
 * 
 * @author zhangyan
 * @date 2024-06-08
 */
@Service
public class TrafficServiceImpl extends ServiceImpl<TrafficMapper, Traffic> implements ITrafficService
{
    @Autowired
    private TrafficMapper trafficMapper;

    /**
     * 查询交通信息
     * 
     * @param id 交通信息主键
     * @return 交通信息
     */
    @Override
    public Traffic selectTrafficById(Long id)
    {
        return trafficMapper.selectTrafficById(id);
    }

    /**
     * 查询交通信息列表
     * 
     * @param traffic 交通信息
     * @return 交通信息
     */
    @Override
    public List<Traffic> selectTrafficList(Traffic traffic)
    {
        return trafficMapper.selectTrafficList(traffic);
    }

    /**
     * 新增交通信息
     * 
     * @param traffic 交通信息
     * @return 结果
     */
    @Override
    public int insertTraffic(Traffic traffic)
    {
        traffic.setCreateTime(DateUtils.getNowDate());
        return trafficMapper.insertTraffic(traffic);
    }

    /**
     * 修改交通信息
     * 
     * @param traffic 交通信息
     * @return 结果
     */
    @Override
    public int updateTraffic(Traffic traffic)
    {
        traffic.setUpdateTime(DateUtils.getNowDate());
        return trafficMapper.updateTraffic(traffic);
    }

    /**
     * 批量删除交通信息
     * 
     * @param ids 需要删除的交通信息主键
     * @return 结果
     */
    @Override
    public int deleteTrafficByIds(Long[] ids)
    {
        return trafficMapper.deleteTrafficByIds(ids);
    }

    /**
     * 删除交通信息信息
     * 
     * @param id 交通信息主键
     * @return 结果
     */
    @Override
    public int deleteTrafficById(Long id)
    {
        return trafficMapper.deleteTrafficById(id);
    }
}

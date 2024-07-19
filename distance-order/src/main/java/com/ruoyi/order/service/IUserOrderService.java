package com.ruoyi.order.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.order.entity.dos.UserOrder;

import java.util.List;

/**
 * 用户订单Service接口
 *
 * @author zhangyan
 * @date 2024-06-15
 */
public interface IUserOrderService  extends IService<UserOrder>
{
    /**
     * 查询用户订单
     *
     * @param id 用户订单主键
     * @return 用户订单
     */
    public UserOrder selectUserOrderById(Long id);

    /**
     * 查询用户订单列表
     *
     * @param userOrder 用户订单
     * @return 用户订单集合
     */
    public List<UserOrder> selectUserOrderList(UserOrder userOrder);

    /**
     * 新增用户订单
     *
     * @param userOrder 用户订单
     * @return 结果
     */
    public int insertUserOrder(UserOrder userOrder);

    /**
     * 修改用户订单
     *
     * @param userOrder 用户订单
     * @return 结果
     */
    public int updateUserOrder(UserOrder userOrder);

    /**
     * 批量删除用户订单
     *
     * @param ids 需要删除的用户订单主键集合
     * @return 结果
     */
    public int deleteUserOrderByIds(Long[] ids);

    /**
     * 删除用户订单信息
     *
     * @param id 用户订单主键
     * @return 结果
     */
    public int deleteUserOrderById(Long id);
}

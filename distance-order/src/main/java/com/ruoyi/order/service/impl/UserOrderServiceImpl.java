package com.ruoyi.order.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.core.utils.uuid.IdUtils;
import com.ruoyi.common.core.utils.uuid.UUID;
import com.ruoyi.common.rocketmq.channel.DolphinSource;
import com.ruoyi.common.rocketmq.constant.tags.OrderTagsEnum;
import com.ruoyi.distance.api.RemoteAppUserService;
import com.ruoyi.distance.api.RemoteDataService;
import com.ruoyi.distance.api.domain.Shop;
import com.ruoyi.distance.api.domain.ShopEvent;
import com.ruoyi.distance.api.domain.User;
import com.ruoyi.order.entity.dos.UserOrder;
import com.ruoyi.order.mapper.UserOrderMapper;
import com.ruoyi.order.service.IUserOrderService;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 用户订单Service业务层处理
 *
 * @author zhangyan
 * @date 2024-06-15
 */
@Service
public class UserOrderServiceImpl extends ServiceImpl<UserOrderMapper, UserOrder> implements IUserOrderService {

    @Autowired
    private UserOrderMapper userOrderMapper;


    @Autowired
    private RemoteDataService remoteDataService;

    @Autowired
    private RemoteAppUserService remoteAppUserService;

    @Autowired
    private DolphinSource source;

    /**
     * 查询用户订单
     *
     * @param id 用户订单主键
     * @return 用户订单
     */
    @Override
    public UserOrder selectUserOrderById(Long id) {
        return userOrderMapper.selectUserOrderById(id);
    }

    /**
     * 查询用户订单列表
     *
     * @param userOrder 用户订单
     * @return 用户订单
     */
    @Override
    public List<UserOrder> selectUserOrderList(UserOrder userOrder) {
        return userOrderMapper.selectUserOrderList(userOrder);
    }

    /**
     * 新增用户订单
     *
     * @param userOrder 用户订单
     * @return 结果
     */
    @Override
    public int insertUserOrder(UserOrder userOrder) {
        Date date = DateUtils.getNowDate();
        userOrder.setCreateTime(date);


        //根据userId查询用户信息
        R<User> userInfo = remoteAppUserService.getInfo(userOrder.getUserId(), SecurityConstants.INNER);
        userOrder.setUserName(userInfo.getData().getUserName());

        //根据shopId查询店铺信息
        R<Shop> shopInfo = remoteDataService.getShopInfo(userOrder.getShopId(), SecurityConstants.INNER);
        userOrder.setName(shopInfo.getData().getName());

        //根据shopEventId查询店铺活动信息
        R<ShopEvent> shopEventInfo = remoteDataService.getShopEventInfo(userOrder.getShopEventId(), SecurityConstants.INNER);
        userOrder.setEventName(shopEventInfo.getData().getEventName());

        userOrder.setOrderNumber(IdUtils.fastUUID());
        userOrder.setAmount(Long.valueOf(shopEventInfo.getData().getAmount()));
        userOrder.setTotalAmount(Long.parseLong(shopEventInfo.getData().getAmount()) * userOrder.getItemCount());

        userOrder.setPaymentTime(date);
        userOrder.setStatus("3");
        userOrder.setRemark(shopEventInfo.getData().getEventLogo());


        String transactionId = UUID.randomUUID().toString();
        source.orderOutput().send(
                MessageBuilder.withPayload(userOrder)
                        .setHeader(RocketMQHeaders.TAGS, OrderTagsEnum.ORDER_CREATE)
                        .setHeader(RocketMQHeaders.TRANSACTION_ID, transactionId)
                        .build()
        );

        System.out.println("发送订单消息");

        return userOrderMapper.insertUserOrder(userOrder);
    }

    /**
     * 修改用户订单
     *
     * @param userOrder 用户订单
     * @return 结果
     */
    @Override
    public int updateUserOrder(UserOrder userOrder) {
        userOrder.setUpdateTime(DateUtils.getNowDate());
        return userOrderMapper.updateUserOrder(userOrder);
    }

    /**
     * 批量删除用户订单
     *
     * @param ids 需要删除的用户订单主键
     * @return 结果
     */
    @Override
    public int deleteUserOrderByIds(Long[] ids) {
        return userOrderMapper.deleteUserOrderByIds(ids);
    }

    /**
     * 删除用户订单信息
     *
     * @param id 用户订单主键
     * @return 结果
     */
    @Override
    public int deleteUserOrderById(Long id) {
        return userOrderMapper.deleteUserOrderById(id);
    }
}

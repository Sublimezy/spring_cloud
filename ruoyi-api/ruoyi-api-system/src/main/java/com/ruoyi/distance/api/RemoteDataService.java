package com.ruoyi.distance.api;

import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.constant.ServiceNameConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.distance.api.domain.Shop;
import com.ruoyi.distance.api.domain.ShopEvent;
import com.ruoyi.distance.api.domain.User;
import com.ruoyi.distance.api.factory.RemoteDataFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * 用户服务
 *
 * @author ruoyi
 */
@FeignClient(contextId = "remoteDataService", value = ServiceNameConstants.DISTANCE_DATA_SERVICE, fallbackFactory = RemoteDataFallbackFactory.class)
public interface RemoteDataService {

    /**
     * 新增商铺
     */
    @PostMapping("/shop")
    public R<Boolean> add(@RequestBody Shop shop, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);


    @GetMapping("/shop/userId/{id}")
    public R<Shop> getInfoByUserId(@PathVariable("id") Long id, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    @GetMapping("/shop/loadShopData")
    public R<Boolean> loadShopData(@RequestHeader(SecurityConstants.FROM_SOURCE) String source);


    /**
     * 获取shop详细信息
     */
    @GetMapping("/shop/{id}")
    public R<Shop> getShopInfo(@PathVariable("id") Long id, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    /**
     * 获取shopEvent详细信息
     */
    @GetMapping("/shopEvent/{id}")
    public R<ShopEvent> getShopEventInfo(@PathVariable("id") Long id, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);
}

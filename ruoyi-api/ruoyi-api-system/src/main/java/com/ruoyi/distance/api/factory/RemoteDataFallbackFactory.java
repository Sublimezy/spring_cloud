package com.ruoyi.distance.api.factory;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.distance.api.RemoteDataService;
import com.ruoyi.distance.api.domain.Shop;
import com.ruoyi.distance.api.domain.ShopEvent;
import com.ruoyi.distance.api.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * 用户服务降级处理
 * 
 * @author ruoyi
 */
@Component
public class RemoteDataFallbackFactory implements FallbackFactory<RemoteDataService>
{
    private static final Logger log = LoggerFactory.getLogger(RemoteDataFallbackFactory.class);

    @Override
    public RemoteDataService create(Throwable throwable)
    {
        log.error("商铺服务调用失败:{}", throwable.getMessage());
        return new RemoteDataService()
        {
            @Override
            public R<Boolean> add(Shop shop, String source) {
                return R.fail("新增商铺失败:" + throwable.getMessage());
            }

            @Override
            public R<Shop> getInfoByUserId(Long id, String source) {
                return R.fail("查询商铺信息失败:" + throwable.getMessage());
            }

            @Override
            public R<Boolean> loadShopData(String source) {
                return R.fail("生成店铺坐标失败:" + throwable.getMessage());
            }

            @Override
            public R<Shop> getShopInfo(Long id, String source) {
                return R.fail("查询商铺信息失败:" + throwable.getMessage());
            }

            @Override
            public R<ShopEvent> getShopEventInfo(Long id, String source) {
                return R.fail("查询商铺活动失败:" + throwable.getMessage());
            }


        };
    }
}

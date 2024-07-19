package com.ruoyi.job.task.data;


import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.distance.api.RemoteAppUserService;
import com.ruoyi.distance.api.RemoteDataService;
import com.ruoyi.distance.api.factory.RemoteDataFallbackFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ShopTack {

    @Autowired
    private RemoteDataService remoteDataService;


    public void loadShopData() {
        System.out.println("远程调用data生成坐标");
        R<?> registerResult = remoteDataService.loadShopData(SecurityConstants.INNER);
    }

}

package com.ruoyi.distance.api.factory;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.distance.api.RemoteAppUserService;
import com.ruoyi.distance.api.domain.User;
import com.ruoyi.system.api.model.LoginUser;
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
public class RemoteAppUserFallbackFactory implements FallbackFactory<RemoteAppUserService>
{
    private static final Logger log = LoggerFactory.getLogger(RemoteAppUserFallbackFactory.class);

    @Override
    public RemoteAppUserService create(Throwable throwable)
    {
        log.error("用户服务调用失败:{}", throwable.getMessage());
        return new RemoteAppUserService()
        {

            @Override
            public R<LoginUser> getAppUserInfo(String username, String source) {
                return R.fail("用userName获取用户失败:" + throwable.getMessage());
            }

            @Override
            public R<User> registerAppUserInfo(User user, String source) {
                return R.fail("获取用户失败:" + throwable.getMessage());

            }

            @Override
            public R<User> getAddShopInfo(Long userId, String source) {
                return R.fail("用userId获取用户失败:" + throwable.getMessage());
            }

            @Override
            public R<User> getInfo(Long userId, String source) {
                return R.fail("用userId获取用户失败:" + throwable.getMessage());}

        };
    }
}

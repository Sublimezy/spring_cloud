package com.ruoyi.distance.api;

import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.distance.api.domain.User;
import com.ruoyi.distance.api.factory.RemoteAppUserFallbackFactory;
import com.ruoyi.system.api.domain.SysUser;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.constant.ServiceNameConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.api.model.LoginUser;

import static com.ruoyi.common.core.domain.R.ok;

/**
 * 用户服务
 *
 * @author ruoyi
 */
@FeignClient(contextId = "remoteAppUserService", value = ServiceNameConstants.DISTANCE_USER_SERVICE, fallbackFactory = RemoteAppUserFallbackFactory.class)
public interface RemoteAppUserService {

    /**
     * 通过用户名查询用户信息 -- app用户
     *
     * @param username 用户名
     * @param source   请求来源
     * @return 结果
     */
    @GetMapping("/appUser/info/{username}")
    public R<LoginUser> getAppUserInfo(@PathVariable("username") String username, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);


    /**
     * 注册用户信息
     *
     * @param source 请求来源
     * @return 结果
     */
    @PostMapping("/appUser/register")
    public R<User> registerAppUserInfo(@RequestBody User user, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);


    //添加店铺 sel_flag=1
    @GetMapping("/appUser/addShop/{userId}")
    public R<User> getAddShopInfo(@PathVariable("userId") Long userId, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);


    /**
     * 获取用户信息详细信息
     */
    @GetMapping("/appUser/{userId}")
    public R<User> getInfo(@PathVariable("userId") Long userId, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);





}


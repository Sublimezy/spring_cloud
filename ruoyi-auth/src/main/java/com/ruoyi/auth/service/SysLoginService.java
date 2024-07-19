package com.ruoyi.auth.service;

import com.ruoyi.auth.form.RegisterBody;
import com.ruoyi.common.core.constant.*;
import com.ruoyi.distance.api.RemoteDataService;
import com.ruoyi.distance.api.domain.Shop;
import com.ruoyi.distance.api.domain.User;
import com.ruoyi.system.api.RemoteUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.enums.UserStatus;
import com.ruoyi.common.core.exception.ServiceException;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.core.utils.ip.IpUtils;
import com.ruoyi.common.redis.service.RedisService;
import com.ruoyi.distance.api.RemoteAppUserService;
import com.ruoyi.system.api.domain.SysUser;
import com.ruoyi.system.api.model.LoginUser;

/**
 * 登录校验方法
 *
 * @author ruoyi
 */
@Component
public class SysLoginService {
    @Autowired
    private RemoteUserService remoteUserService;

    @Autowired
    private RemoteAppUserService remoteAppUserService;
    @Autowired
    private RemoteDataService remoteDataService;

    @Autowired
    private SysPasswordService passwordService;

    @Autowired
    private SysRecordLogService recordLogService;

    @Autowired
    private RedisService redisService;

    /**
     * 登录
     */
    public LoginUser login(String username, String password, String resource) {
        // 用户名或密码为空 错误
        if (StringUtils.isAnyBlank(username, password)) {
            recordLogService.recordLogininfor(username, Constants.LOGIN_FAIL, "用户/密码必须填写");
            throw new ServiceException("用户/密码必须填写");
        }
        // 密码如果不在指定范围内 错误
        if (password.length() < UserConstants.PASSWORD_MIN_LENGTH
                || password.length() > UserConstants.PASSWORD_MAX_LENGTH) {
            recordLogService.recordLogininfor(username, Constants.LOGIN_FAIL, "用户密码不在指定范围");
            throw new ServiceException("用户密码不在指定范围");
        }
        // 用户名不在指定范围内 错误
        if (username.length() < UserConstants.USERNAME_MIN_LENGTH
                || username.length() > UserConstants.USERNAME_MAX_LENGTH) {
            recordLogService.recordLogininfor(username, Constants.LOGIN_FAIL, "用户名不在指定范围");
            throw new ServiceException("用户名不在指定范围");
        }
        // IP黑名单校验
        String blackStr = Convert.toStr(redisService.getCacheObject(CacheConstants.SYS_LOGIN_BLACKIPLIST));
        if (IpUtils.isMatchedIp(blackStr, IpUtils.getIpAddr())) {
            recordLogService.recordLogininfor(username, Constants.LOGIN_FAIL, "很遗憾，访问IP已被列入系统黑名单");
            throw new ServiceException("很遗憾，访问IP已被列入系统黑名单");
        }


        // 查询用户信息
        R<LoginUser> userResult = null;
        System.out.println("当前登陆系统来源-----" + resource);
        if (resource.equals(UserResourceConstants.APP_USER) || resource.equals(UserResourceConstants.SELLER_USER)) {
            System.out.println("调用distance-user");
            userResult = remoteAppUserService.getAppUserInfo(username, SecurityConstants.INNER);
        } else if (resource.equals(UserResourceConstants.SYSTEM_USER)) {
            System.out.println("调用system-user");
            userResult = remoteUserService.getUserInfo(username, SecurityConstants.INNER);
        }


        if (StringUtils.isNull(userResult) || StringUtils.isNull(userResult.getData())) {
            recordLogService.recordLogininfor(username, Constants.LOGIN_FAIL, "登录用户不存在");
            throw new ServiceException("登录用户：" + username + " 不存在");
        }

        if (R.FAIL == userResult.getCode()) {
            throw new ServiceException(userResult.getMsg());
        }

        LoginUser userInfo = userResult.getData();
        SysUser user = userResult.getData().getSysUser();
        if (UserStatus.DELETED.getCode().equals(user.getDelFlag())) {
            recordLogService.recordLogininfor(username, Constants.LOGIN_FAIL, "对不起，您的账号已被删除");
            throw new ServiceException("对不起，您的账号：" + username + " 已被删除");
        }
        if (UserStatus.DISABLE.getCode().equals(user.getStatus())) {
            recordLogService.recordLogininfor(username, Constants.LOGIN_FAIL, "用户已停用，请联系管理员");
            throw new ServiceException("对不起，您的账号：" + username + " 已停用");
        }
        passwordService.validate(user, password);
        recordLogService.recordLogininfor(username, Constants.LOGIN_SUCCESS, "登录成功");
        return userInfo;
    }

    public void logout(String loginName) {
        recordLogService.recordLogininfor(loginName, Constants.LOGOUT, "退出成功");
    }

    /**
     * 注册
     */
    public void register(RegisterBody registerBody) {

        String username = registerBody.getUserName();
        String password = registerBody.getPassword();

        // 用户名或密码为空 错误
        if (StringUtils.isAnyBlank(registerBody.getUserName(), registerBody.getPassword())) {
            throw new ServiceException("用户/密码必须填写");
        }
        if (username.length() < UserConstants.USERNAME_MIN_LENGTH
                || username.length() > UserConstants.USERNAME_MAX_LENGTH) {
            throw new ServiceException("账户长度必须在2到20个字符之间");
        }
        if (password.length() < UserConstants.PASSWORD_MIN_LENGTH
                || password.length() > UserConstants.PASSWORD_MAX_LENGTH) {
            throw new ServiceException("密码长度必须在5到20个字符之间");
        }
        String resource = registerBody.getSource();

        // 注册用户信息
        User user = new User();

        R<?> registerResult = null;
        BeanUtils.copyProperties(registerBody, user);
        System.out.println("当前登陆系统来源-----" + resource);
        if (resource.equals(UserResourceConstants.APP_USER)) {
            registerResult = remoteAppUserService.registerAppUserInfo(user, SecurityConstants.INNER);
            if (R.FAIL == registerResult.getCode()) {
                throw new ServiceException(registerResult.getMsg());
            }
        } else if (resource.equals(UserResourceConstants.SELLER_USER)) {
            user.setDelFlag(1L);
            registerResult = remoteAppUserService.registerAppUserInfo(user, SecurityConstants.INNER);

            if (R.FAIL == registerResult.getCode()) {
                throw new ServiceException(registerResult.getMsg());
            }


            Shop shop = new Shop();
            User newUser = (User) registerResult.getData();
            System.out.println(registerBody.getAvgPrice());
            BeanUtils.copyProperties(registerBody, shop);
            System.out.println(shop.getAvgPrice());


            shop.setUserId(newUser.getUserId());


//调用shop
            R<?> addShopResult = remoteDataService.add(shop, SecurityConstants.INNER);


            if (R.FAIL == addShopResult.getCode()) {
                throw new ServiceException(addShopResult.getMsg());
            }


        }


        recordLogService.recordLogininfor(username, Constants.REGISTER, "注册成功");
    }
}

package com.ruoyi.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.constant.UserConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.distance.api.RemoteDataService;
import com.ruoyi.distance.api.domain.Shop;
import com.ruoyi.user.entity.dos.User;
import com.ruoyi.user.entity.vos.UserListIdVO;
import com.ruoyi.user.mapper.UserMapper;
import com.ruoyi.user.service.IUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户信息Service业务层处理
 *
 * @author zhangyan
 * @date 2024-06-08
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RemoteDataService remoteDataService;

    /**
     * 查询用户信息
     *
     * @param userId 用户信息主键
     * @return 用户信息
     */
    @Override
    public User selectUserByUserId(Long userId) {
        return userMapper.selectUserByUserId(userId);
    }

    /**
     * 查询用户信息列表
     *
     * @param user 用户信息
     * @return 用户信息
     */
    @Override
    public List<User> selectUserList(User user) {
        return userMapper.selectUserList(user);
    }

    @Override
    public List<UserListIdVO> selectUserListId() {
//mybatis
//        return userMapper.selectListUserListIdVO();
        // 创建 QueryWrapper 对象
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("del_flag", 0L);
        // 查询指定字段
        queryWrapper.select("user_id", "user_name");

        // 执行查询，并将结果映射到 User 类中
        List<User> userList = userMapper.selectList(queryWrapper);

        // 创建 UserListIdVO 对象列表
        List<UserListIdVO> userListIdVOList = new ArrayList<>();

        // 将 User 对象列表转换为 UserListIdVO 对象列表
        for (User user : userList) {
            UserListIdVO userListIdVO = new UserListIdVO();
            // 使用 BeanUtils 工具类将 User 对象的属性拷贝到 UserListIdVO 对象中
            BeanUtils.copyProperties(user, userListIdVO);
            userListIdVOList.add(userListIdVO);
        }

        return userListIdVOList;
    }

    /**
     * 新增用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public User insertUser(User user) {
        user.setPassword(SecurityUtils.encryptPassword(user.getPassword()));
        if(user.getAvatar()==null){
            user.setAvatar("http://127.0.0.1:9000/shop2/2024/06/10/QQ%E6%88%AA%E5%9B%BE20231012132920_20240610223651A005.png");
        }
        save(user);
        return user;

    }

    /**
     * 修改用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public int updateUser(User user) {
        return userMapper.updateUser(user);
    }

    /**
     * 批量删除用户信息
     *
     * @param userIds 需要删除的用户信息主键
     * @return 结果
     */
    @Override
    public int deleteUserByUserIds(Long[] userIds) {
        return userMapper.deleteUserByUserIds(userIds);
    }

    /**
     * 删除用户信息信息
     *
     * @param userId 用户信息主键
     * @return 结果
     */
    @Override
    public int deleteUserByUserId(Long userId) {
        return userMapper.deleteUserByUserId(userId);
    }

    @Override
    public boolean checkUserNameUnique(User user) {
        Long userId = StringUtils.isNull(user.getUserId()) ? -1L : user.getUserId();
        User info = userMapper.checkUserNameUnique(user.getUserName());
        if (StringUtils.isNotNull(info) && info.getUserId().longValue() != userId.longValue()) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    @Override
    public User selectUserByUserName(String username) {
        return userMapper.selectUserByUserName(username);
    }

    @Override
    public User getAppUserInfo(Long userId) {
        User user = getById(userId);
        // 查询用户信息
        R<Shop> userResult = remoteDataService.getInfoByUserId(userId, SecurityConstants.INNER);
        if(userResult.getData()!=null){
            Shop shop= userResult.getData();
            user.setShopId(shop.getId());
        }
        return user;
    }

    @Override
    public User getAddShopInfo(Long userId) {

        User user = getById(userId);
        user.setDelFlag(1L);
        updateById(user);
        return user;
    }


}

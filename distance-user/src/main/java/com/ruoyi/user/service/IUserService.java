package com.ruoyi.user.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.user.entity.dos.User;
import com.ruoyi.user.entity.vos.UserListIdVO;

/**
 * 用户信息Service接口
 * 
 * @author zhangyan
 * @date 2024-06-08
 */
public interface IUserService extends IService<User>
{
    /**
     * 查询用户信息
     * 
     * @param userId 用户信息主键
     * @return 用户信息
     */
    public User selectUserByUserId(Long userId);

    /**
     * 查询用户信息列表
     * 
     * @param user 用户信息
     * @return 用户信息集合
     */
    public List<User> selectUserList(User user);

    public List<UserListIdVO> selectUserListId();
    /**
     * 新增用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    public User insertUser(User user);

    /**
     * 修改用户信息
     * 
     * @param user 用户信息
     * @return 结果
     */
    public int updateUser(User user);

    /**
     * 批量删除用户信息
     * 
     * @param userIds 需要删除的用户信息主键集合
     * @return 结果
     */
    public int deleteUserByUserIds(Long[] userIds);

    /**
     * 删除用户信息信息
     * 
     * @param userId 用户信息主键
     * @return 结果
     */
    public int deleteUserByUserId(Long userId);

    boolean checkUserNameUnique( User user);


    User selectUserByUserName(String username);

    User getAppUserInfo(Long userId);

    User getAddShopInfo(Long userId);
}

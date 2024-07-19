package com.ruoyi.user.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.system.api.domain.SysUser;
import com.ruoyi.user.entity.dos.User;
import com.ruoyi.user.entity.vos.UserListIdVO;
import org.apache.ibatis.annotations.Select;

/**
 * 用户信息Mapper接口
 * 
 * @author zhangyan
 * @date 2024-06-08
 */
public interface UserMapper extends BaseMapper<User>
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

    /**
     * 新增用户信息
     * 
     * @param user 用户信息
     * @return 结果
     */
    public int insertUser(User user);

    /**
     * 修改用户信息
     * 
     * @param user 用户信息
     * @return 结果
     */
    public int updateUser(User user);

    /**
     * 删除用户信息
     * 
     * @param userId 用户信息主键
     * @return 结果
     */
    public int deleteUserByUserId(Long userId);

    /**
     * 批量删除用户信息
     * 
     * @param userIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteUserByUserIds(Long[] userIds);



    @Select("SELECT user_id AS userId, user_name AS userName FROM user")
    List<UserListIdVO> selectListUserListIdVO();

    User checkUserNameUnique(String userName);



    public User selectUserByUserName(String userName);

}

package com.ruoyi.user.controller;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.core.utils.bean.BeanUtils;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.common.security.annotation.InnerAuth;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.system.api.domain.SysUser;
import com.ruoyi.system.api.model.LoginUser;
import com.ruoyi.user.entity.dos.User;
import com.ruoyi.user.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static com.ruoyi.common.core.domain.R.ok;

/**
 * 用户信息Controller
 *
 * @author zhangyan
 * @date 2024-06-08
 */
@RestController
@RequestMapping("/appUser")
public class UserController extends BaseController {


    @Autowired
    private IUserService userService;

    /**
     * 获取当前用户信息
     */
    @InnerAuth
    @GetMapping("/info/{username}")
    public R<LoginUser> info(@PathVariable("username") String username) {
        User user = userService.selectUserByUserName(username);
        if (StringUtils.isNull(user)) {
            return R.fail("用户名或密码错误");
        }

        LoginUser sysUserVo = new LoginUser();
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(user, sysUser);
        sysUserVo.setSysUser(sysUser);
        return ok(sysUserVo);
    }

    /**
     * 查询用户信息列表
     */
    @RequiresPermissions("app-user:app-user:list")
    @GetMapping("/list")
    public TableDataInfo list(User user) {
        startPage();
        List<User> list = userService.selectUserList(user);
        return getDataTable(list);
    }

    /**
     * 查询用户信息列表
     */
    @RequiresPermissions("app-user:app-user:list-id")
    @GetMapping("/listId")
    public AjaxResult listId() {
        return success(userService.selectUserListId());
    }


    /**
     * 导出用户信息列表
     */
    @RequiresPermissions("app-user:app-user:export")
    @PostMapping("/export")
    public void export(HttpServletResponse response, User user) {
        List<User> list = userService.selectUserList(user);
        ExcelUtil<User> util = new ExcelUtil<User>(User.class);
        util.exportExcel(response, list, "用户信息数据");
    }

    /**
     * 获取用户信息详细信息
     */
    @GetMapping(value = "/{userId}")
    public R<User> getInfo(@PathVariable("userId") Long userId) {
        return ok(userService.selectUserByUserId(userId));
    }

    /**
     * 获取修改用户信息详细信息
     */
    @GetMapping(value = "/addShop/{userId}")
    public R<User> getAddShopInfo(@PathVariable("userId") Long userId) {
        return ok(userService.getAddShopInfo(userId));
    }

    /**
     * 新增用户信息
     */
    @RequiresPermissions("app-user:app-user:add")
    @PostMapping
    public R<User> add(@RequestBody User user) {
        String username = user.getUserName();

        if (!userService.checkUserNameUnique(user)) {
            return R.fail("保存用户'" + username + "'失败，注册账号已存在");
        }
        return ok(userService.insertUser(user));

    }

    /**
     * 修改用户信息
     */
    @RequiresPermissions("app-user:app-user:edit")
    @PutMapping
    public AjaxResult edit(@RequestBody User user) {
        String username = user.getUserName();

        if (!userService.checkUserNameUnique(user)) {
            return error("保存用户'" + username + "'失败，注册账号已存在");
        }
        return toAjax(userService.updateUser(user));
    }

    /**
     * 删除用户信息
     */
    @RequiresPermissions("app-user:app-user:remove")
    @DeleteMapping("/{userIds}")
    public AjaxResult remove(@PathVariable Long[] userIds) {
        return toAjax(userService.deleteUserByUserIds(userIds));
    }


    /**
     * 注册用户信息
     */
    @InnerAuth
    @PostMapping("/register")
    public R<User> register(@RequestBody User user) {

        if (!userService.checkUserNameUnique(user)) {
            return R.fail("用户名不唯一");
        }

        return ok(userService.insertUser(user));
    }


    /**
     * 获取用户信息
     *
     * @return 用户信息
     */
    @GetMapping("getAppUserInfo")
    public AjaxResult getAppUserInfo() {
        User user = userService.getAppUserInfo(SecurityUtils.getUserId());

        AjaxResult ajax = AjaxResult.success();
        ajax.put("user", user);
        ajax.put("roles", null);
        ajax.put("permissions", null);

        return ajax;
    }


}

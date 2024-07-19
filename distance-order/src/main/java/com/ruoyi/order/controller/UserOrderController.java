package com.ruoyi.order.controller;

import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.order.entity.dos.UserOrder;
import com.ruoyi.order.service.IUserOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 用户订单Controller
 *
 * @author zhangyan
 * @date 2024-06-15
 */
@RestController
@RequestMapping("/order")
public class UserOrderController extends BaseController
{
    @Autowired
    private IUserOrderService userOrderService;

    /**
     * 查询用户订单列表
     */
    @GetMapping("/list")
    public TableDataInfo list(UserOrder userOrder)
    {
        startPage();
        List<UserOrder> list = userOrderService.selectUserOrderList(userOrder);
        return getDataTable(list);
    }

    /**
     * 导出用户订单列表
     */
    @Log(title = "用户订单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, UserOrder userOrder)
    {
        List<UserOrder> list = userOrderService.selectUserOrderList(userOrder);
        ExcelUtil<UserOrder> util = new ExcelUtil<UserOrder>(UserOrder.class);
        util.exportExcel(response, list, "用户订单数据");
    }

    /**
     * 获取用户订单详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(userOrderService.selectUserOrderById(id));
    }

    /**
     * 新增用户订单
     */
    @Log(title = "用户订单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody UserOrder userOrder)
    {
        return toAjax(userOrderService.insertUserOrder(userOrder));
    }

    /**
     * 修改用户订单
     */
    @Log(title = "用户订单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody UserOrder userOrder)
    {
        return toAjax(userOrderService.updateUserOrder(userOrder));
    }

    /**
     * 删除用户订单
     */
    @Log(title = "用户订单", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(userOrderService.deleteUserOrderByIds(ids));
    }
}

package com.ruoyi.data.controller;

import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.data.entity.dos.ShopEvent;
import com.ruoyi.data.service.IShopEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ruoyi.common.security.annotation.RequiresPermissions;

import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.page.TableDataInfo;

/**
 * 店铺活动Controller
 *
 * @author zhangyan
 * @date 2024-06-10
 */
@RestController
@RequestMapping("/shopEvent")
public class ShopEventController extends BaseController {
    @Autowired
    private IShopEventService shopEventService;

    /**
     * 查询店铺活动列表
     */

    @GetMapping("/list")
    public TableDataInfo list(ShopEvent shopEvent) {
        startPage();
        List<ShopEvent> list = shopEventService.selectShopEventList(shopEvent);
        return getDataTable(list);
    }


    @GetMapping("/hotList")
    public TableDataInfo getHotList(ShopEvent shopEvent) {
        startPage();
        List<ShopEvent> list = shopEventService.selectShopEventList(shopEvent);
        return getDataTable(list);
    }



    /**
     * 导出店铺活动列表
     */

    @PostMapping("/export")
    public void export(HttpServletResponse response, ShopEvent shopEvent) {
        List<ShopEvent> list = shopEventService.selectShopEventList(shopEvent);
        ExcelUtil<ShopEvent> util = new ExcelUtil<ShopEvent>(ShopEvent.class);
        util.exportExcel(response, list, "店铺活动数据");
    }

    /**
     * 获取店铺活动详细信息
     */

    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(shopEventService.selectShopEventById(id));
    }

    /**
     * 新增店铺活动
     */

    @PostMapping(value = "/{id}")
    public AjaxResult add(@PathVariable("id") Long id, @RequestBody ShopEvent shopEvent) {
        return toAjax(shopEventService.insertShopEvent(id, shopEvent));
    }

    /**
     * 修改店铺活动
     */

    @PutMapping
    public AjaxResult edit(@RequestBody ShopEvent shopEvent) {
        return toAjax(shopEventService.updateShopEvent(shopEvent));
    }

    /**
     * 删除店铺活动
     */

    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(shopEventService.deleteShopEventByIds(ids));
    }
}

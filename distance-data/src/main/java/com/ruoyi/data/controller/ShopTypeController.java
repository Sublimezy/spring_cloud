package com.ruoyi.data.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.data.entity.dos.ShopType;
import com.ruoyi.data.service.IShopTypeService;
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
 * 店铺Controller
 * 
 * @author zhangyan
 * @date 2024-06-08
 */
@RestController
@RequestMapping("/shopType")
public class ShopTypeController extends BaseController
{
    @Autowired
    private IShopTypeService shopTypeService;

    /**
     * 查询店铺列表
     */
    @GetMapping("/list")
    public TableDataInfo list(ShopType shopType)
    {
        startPage();
        List<ShopType> list = shopTypeService.selectShopTypeList(shopType);
        return getDataTable(list);
    }

    /**
     * 导出店铺列表
     */
    @PostMapping("/export")
    public void export(HttpServletResponse response, ShopType shopType)
    {
        List<ShopType> list = shopTypeService.selectShopTypeList(shopType);
        ExcelUtil<ShopType> util = new ExcelUtil<ShopType>(ShopType.class);
        util.exportExcel(response, list, "店铺数据");
    }

    /**
     * 获取店铺详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(shopTypeService.selectShopTypeById(id));
    }

    /**
     * 新增店铺
     */
    @PostMapping
    public AjaxResult add(@RequestBody ShopType shopType)
    {
        return toAjax(shopTypeService.insertShopType(shopType));
    }

    /**
     * 修改店铺
     */
    @PutMapping
    public AjaxResult edit(@RequestBody ShopType shopType)
    {
        return toAjax(shopTypeService.updateShopType(shopType));
    }

    /**
     * 删除店铺
     */
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(shopTypeService.deleteShopTypeByIds(ids));
    }


}

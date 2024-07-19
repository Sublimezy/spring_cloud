package com.ruoyi.data.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
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
import com.ruoyi.data.entity.dos.Traffic;
import com.ruoyi.data.service.ITrafficService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.page.TableDataInfo;

/**
 * 交通信息Controller
 * 
 * @author zhangyan
 * @date 2024-06-08
 */
@RestController
@RequestMapping("/traffic")
public class TrafficController extends BaseController
{
    @Autowired
    private ITrafficService trafficService;

    /**
     * 查询交通信息列表
     */
    @GetMapping("/list")
    public TableDataInfo list(Traffic traffic)
    {
        startPage();
        List<Traffic> list = trafficService.selectTrafficList(traffic);
        return getDataTable(list);
    }

    /**
     * 导出交通信息列表
     */
    @PostMapping("/export")
    public void export(HttpServletResponse response, Traffic traffic)
    {
        List<Traffic> list = trafficService.selectTrafficList(traffic);
        ExcelUtil<Traffic> util = new ExcelUtil<Traffic>(Traffic.class);
        util.exportExcel(response, list, "交通信息数据");
    }

    /**
     * 获取交通信息详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(trafficService.selectTrafficById(id));
    }

    /**
     * 新增交通信息
     */
    @PostMapping
    public AjaxResult add(@RequestBody Traffic traffic)
    {
        return toAjax(trafficService.insertTraffic(traffic));
    }

    /**
     * 修改交通信息
     */
    @PutMapping
    public AjaxResult edit(@RequestBody Traffic traffic)
    {
        return toAjax(trafficService.updateTraffic(traffic));
    }

    /**
     * 删除交通信息
     */
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(trafficService.deleteTrafficByIds(ids));
    }
}

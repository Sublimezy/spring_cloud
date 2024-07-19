package com.ruoyi.data.controller;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.common.rocketmq.channel.DolphinSource;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import com.ruoyi.data.entity.dos.Shop;
import com.ruoyi.data.service.IShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 店铺Controller
 *
 * @author zhangyan
 * @date 2024-06-08
 */
@RestController
@RequestMapping("/shop")
public class ShopController extends BaseController {


    @Autowired
    private IShopService shopService;


    @Autowired
    private DolphinSource source;

    @GetMapping("/message")
    public Object send(String message) {
        MessageBuilder<String> messageBuilder = MessageBuilder.withPayload(message);
        source.smsOutput().send(messageBuilder.build());
        System.out.println("当前为数据模块 短信消息发送成功：" + message);
        return "当前为数据模块 短信消息发送成功：" + message;
    }


    /**
     * 查询店铺列表
     */
    @GetMapping("/list")
    public TableDataInfo list(Shop shop) {
        startPage();
        List<Shop> list = shopService.selectShopList(shop);
        return getDataTable(list);
    }

    /**
     * 导出店铺列表
     */
    @RequiresPermissions("data:shop:export")
    @PostMapping("/export")
    public void export(HttpServletResponse response, Shop shop) {
        List<Shop> list = shopService.selectShopList(shop);
        ExcelUtil<Shop> util = new ExcelUtil<Shop>(Shop.class);
        util.exportExcel(response, list, "店铺数据");
    }

    /**
     * 获取店铺详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id) {
        return success(shopService.selectShopById(id));
    }

    /**
     * 根据userid
     */
    @GetMapping(value = "/userId/{id}")
    public R<Shop> getInfoByUserId(@PathVariable("id") Long id) {
        return R.ok(shopService.getInfoByUserId(id));
    }


    /**
     * 新增店铺
     */
    @PostMapping
    public R<Boolean> add(@RequestBody Shop shop) {
        if (shopService.insertShop(shop) > 0) {
            return R.ok();
        }
        return R.fail("新增失败");
    }

    /**
     * 修改店铺
     */
    @PutMapping
    public AjaxResult edit(@RequestBody Shop shop) {
        return toAjax(shopService.updateShop(shop));
    }

    /**
     * 删除店铺
     */
    @RequiresPermissions("data:shop:remove")
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids) {
        return toAjax(shopService.deleteShopByIds(ids));
    }

    /**
     * 生成店铺坐标数据
     */
    @GetMapping("/loadShopData")
    public R<Boolean> loadShopData() {
        return R.ok(shopService.loadShopData());
    }


    @GetMapping("/queryShopList")
    public TableDataInfo queryShopList(
            @RequestParam(value = "typeId", required = false) Integer typeId,
            @RequestParam(value = "pageNum") Integer pageNum,
            @RequestParam(value = "pageSize") Integer pageSize,
            @RequestParam(value = "x", required = false) Double x,
            @RequestParam(value = "y", required = false) Double y,
            @RequestParam(value = "distance", required = false) Double distance
    ) {
        return shopService.queryShopList(typeId, pageNum, pageSize, x, y, distance);
    }

    @GetMapping("/indexList")
    public TableDataInfo indexList() {

        return shopService.selectIndexList();

    }

}

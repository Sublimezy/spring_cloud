package com.ruoyi.comments.controller;

import com.ruoyi.comments.entity.dos.Comments;
import com.ruoyi.comments.service.ICommentsService;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 用户评价Controller
 * 
 * @author zhangyan
 * @date 2024-06-08
 */
@RestController
@RequestMapping("/comments")
public class CommentsController extends BaseController
{
    @Autowired
    private ICommentsService commentsService;

    /**
     * 查询用户评价列表
     */
    @GetMapping("/list")
    public TableDataInfo list(Comments comments)
    {
        startPage();
        List<Comments> list = commentsService.selectCommentsList(comments);
        return getDataTable(list);
    }

    /**
     * 导出用户评价列表
     */
    @PostMapping("/export")
    public void export(HttpServletResponse response, Comments comments)
    {
        List<Comments> list = commentsService.selectCommentsList(comments);
        ExcelUtil<Comments> util = new ExcelUtil<Comments>(Comments.class);
        util.exportExcel(response, list, "用户评价数据");
    }

    /**
     * 获取用户评价详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(commentsService.selectCommentsById(id));
    }

    /**
     * 新增用户评价
     */
    @PostMapping
    public AjaxResult add(@RequestBody Comments comments)
    {
        return toAjax(commentsService.insertComments(comments));
    }

    /**
     * 修改用户评价
     */
    @PutMapping
    public AjaxResult edit(@RequestBody Comments comments)
    {
        return toAjax(commentsService.updateComments(comments));
    }

    /**
     * 删除用户评价
     */
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(commentsService.deleteCommentsByIds(ids));
    }
}

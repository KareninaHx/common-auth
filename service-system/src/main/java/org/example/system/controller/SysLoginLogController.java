package org.example.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.example.common.result.Result;
import org.example.model.system.SysLoginLog;
import org.example.model.vo.SysLoginLogQueryVo;
import org.example.system.service.SysLoginLogService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by 27 on 2023/10/3
 */
@Api(value = "SysLoginLog管理", tags = "SysLoginLog管理")
@RestController
@RequestMapping(value="/admin/system/sysLoginLog")
@SuppressWarnings({"unchecked", "rawtypes"})
public class SysLoginLogController {
    @Resource
    private SysLoginLogService sysLoginLogService;

    @ApiOperation(value = "获取分页列表")
    @GetMapping("{page}/{limit}")
    public Result index(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,

            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit,

            @ApiParam(name = "sysLoginLogVo", value = "查询对象", required = false)
            SysLoginLogQueryVo sysLoginLogQueryVo) {
        Page<SysLoginLog> pageParam = new Page<>(page, limit);
        IPage<SysLoginLog> pageModel = sysLoginLogService.selectPage(pageParam, sysLoginLogQueryVo);
        return Result.ok(pageModel);
    }

    //查询当个的记录
    @ApiOperation(value = "获取")
    @GetMapping("get/{id}")
    public Result get(@PathVariable Long id) {
        SysLoginLog sysLoginLog = sysLoginLogService.getById(id);
        return Result.ok(sysLoginLog);
    }
}

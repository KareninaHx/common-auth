package org.example.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.example.common.result.Result;
import org.example.model.system.SysUser;
import org.example.model.vo.SysUserQueryVo;
import org.example.system.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * Created by 27 on 2023/10/1
 */

@Api(tags = "用户管理")
@RestController
@RequestMapping("/admin/system/sysUser")
@CrossOrigin
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;

    @PreAuthorize("hasAuthority('bnt.sysUser.list')")
    @ApiOperation(value = "获取分页列表")
    @GetMapping("/{page}/{limit}")
    public Result index(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,

            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit,

            @ApiParam(name = "userQueryVo", value = "查询对象", required = false)
            SysUserQueryVo userQueryVo) {
        Page<SysUser> pageParam = new Page<>(page, limit);
        IPage<SysUser> pageModel = sysUserService.selectPage(pageParam, userQueryVo);
        return Result.ok(pageModel);
    }

    @PreAuthorize("hasAuthority('bnt.sysUser.list')")
    @ApiOperation(value = "获取用户")
    @GetMapping("/get/{id}")
    public Result get(@PathVariable Long id) {
        SysUser user = sysUserService.getById(id);
        return Result.ok(user);
    }

    @PreAuthorize("hasAuthority('bnt.sysUser.add')")
    @ApiOperation(value = "保存用户")
    @PostMapping("/save")
    public Result save(@RequestBody SysUser user) {
        sysUserService.save(user);
        return Result.ok();
    }

    @PreAuthorize("hasAuthority('bnt.sysUser.update')")
    @ApiOperation(value = "更新用户")
    @PutMapping("/update")
    public Result updateById(@RequestBody SysUser user) {
        sysUserService.updateById(user);
        return Result.ok();
    }

    @PreAuthorize("hasAuthority('bnt.sysUser.remove')")
    @ApiOperation(value = "删除用户")
    @DeleteMapping("/remove/{id}")
    public Result remove(@PathVariable Long id) {
        sysUserService.removeById(id);
        return Result.ok();
    }

    //更改用户状态
    @PreAuthorize("hasAuthority('bnt.sysUser.update')")
    @ApiOperation(value = "更新状态")
    @GetMapping("updateStatus/{id}/{status}")
    public Result updateStatus(@PathVariable Long id, @PathVariable Integer status) {
        sysUserService.updateStatus(id, status);
        return Result.ok();
    }

}

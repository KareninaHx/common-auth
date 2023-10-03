package org.example.system.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.example.common.result.Result;
import org.example.common.result.ResultCodeEnum;
import org.example.common.utils.JwtHelper;
import org.example.common.utils.MD5;
import org.example.model.system.SysUser;
import org.example.model.vo.LoginVo;
import org.example.system.exception.KareninaException;
import org.example.system.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 27 on 2023/10/1
 */


/*
处理用户登录
 */
@Api(tags = "后台登录管理")
@RestController
@RequestMapping("/admin/system/index")
public class IndexController {

    @Autowired
    private SysUserService sysUserService;

    ////用户login
    //@ApiOperation(value = "用户登录")
    //@PostMapping("/login")
    ////code:20000,data:{token:"admin"}
    //public Result userLogin(){
    //    Map<String,Object> map = new HashMap<>();
    //    map.put("token","admin-token");
    //    return Result.ok("map");
    //}
    @ApiOperation(value = "登录")
    @PostMapping("/login")
    public Result login(@RequestBody LoginVo loginVo) {
        SysUser sysUser = sysUserService.getByUsername(loginVo.getUsername());
        if(null == sysUser) {
            throw new KareninaException(ResultCodeEnum.ACCOUNT_ERROR);
        }
        if(!MD5.encrypt(loginVo.getPassword()).equals(sysUser.getPassword())) {
            throw new KareninaException(ResultCodeEnum.PASSWORD_ERROR);
        }
        if(sysUser.getStatus().intValue() == 0) {
            throw new KareninaException(ResultCodeEnum.ACCOUNT_STOP);
        }

        Map<String, Object> map = new HashMap<>();
        map.put("token", JwtHelper.createToken(sysUser.getId(), sysUser.getUsername()));
        return Result.ok(map);
    }

    //获取用户信息 info
    @ApiOperation(value = "获取用户信息")
    @GetMapping("/info")
    public Result info(HttpServletRequest request) {
        String username = JwtHelper.getUsername(request.getHeader("token"));
        Map<String, Object> map = sysUserService.getUserInfo(username);
        return Result.ok(map);
    }

    /*
    退出
     */
    @ApiOperation(value = "用户退出")
    @PostMapping("/logout")
    public Result logout(){
        return Result.ok();
    }

}

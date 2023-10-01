package org.example.system.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.example.common.result.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    //用户login
    @ApiOperation(value = "用户登录")
    @PostMapping("/login")
    //code:20000,data:{token:"admin"}
    public Result userLogin(){
        Map<String,Object> map = new HashMap<>();
        map.put("token","admin-token");
        return Result.ok("map");
    }

    //获取用户信息 info
    @ApiOperation(value="获取用户信息")
    @GetMapping("/info")
    public Result userInfo(){
        Map<String, Object> map = new HashMap<>();
        map.put("roles","[admin]");
        map.put("name","admin");
        map.put("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
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

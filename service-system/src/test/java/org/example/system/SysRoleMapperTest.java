package org.example.system;

import org.example.model.system.SysRole;
import org.example.system.mapper.SysRoleMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * Created by 27 on 2023/9/29
 */
@SpringBootTest
public class SysRoleMapperTest {

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Test
    //测试查询所有角色
    public void testSelectList() {
        System.out.println(("----- selectAll method test ------"));
        //UserMapper 中的 selectList() 方法的参数为 MP 内置的条件封装器 Wrapper
        //所以不填写就是无任何条件
        List<SysRole> users = sysRoleMapper.selectList(null);
        for (SysRole sysRole : users) {
            System.out.println("sysRole = " + sysRole);
        }
    }
    @Test
//    测试添加
    public void testInsert(){
        System.out.println("=========insert a new user");
        //创建一个实例化角色对象
        SysRole sysRole = new SysRole();
        //=====设置属性========
        sysRole.setRoleName("角色管理员");// 名称
        sysRole.setRoleCode("role"); // 角色编码
        sysRole.setDescription("角色管理员"); //角色描述

        int result = sysRoleMapper.insert(sysRole);//判断是否插入成功 ，获取影响的行数
        System.out.println(result); //影响的行数
        System.out.println(sysRole.getId()); //id自动回填
    }

//    测试更新
    @Test
    public void testUpdate(){
        SysRole sysRole = new SysRole();
        //设置查询参数
        sysRole.setId("1");
        sysRole.setRoleName("角色管理员1");
        //根据id 修改信息
        int result = sysRoleMapper.updateById(sysRole);
        System.out.println(result);

    }
}

package org.example.system;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.example.model.system.SysRole;
import org.example.system.service.SysRoleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * Created by 27 on 2023/9/30
 */
@SpringBootTest
public class SysRoleServiceTest {

    @Autowired
    private SysRoleService sysRoleService;

    @Test
    public void testSelectList(){
        System.out.println(("----- selectAll method test ------"));
        //UserMapper 中的 selectList() 方法的参数为 MP 内置的条件封装器 Wrapper
        //所以不填写就是无任何条件
        List<SysRole> roles = sysRoleService.list();//查询所有数据，其中sql语句最后有is_deleted=0
        for (SysRole role : roles) {
            System.out.println("role = " + role);
        }
    }

    @Test
    //测试 插入数据
    public void testInsert(){
        SysRole sysRole = new SysRole();
        //设置自己手动添加的数据
        sysRole.setRoleName("角色管理员");
        sysRole.setRoleCode("role");
        sysRole.setDescription("角色管理员");
        //执行
        boolean result = sysRoleService.save(sysRole);
        System.out.println("成功与否:"+result); //成功还是失败
    }

    @Test
    //测试 修改数据
    public void testUpdateById(){
        SysRole sysRole = new SysRole();
        sysRole.setId("1707610377992314882");
        sysRole.setRoleName("角色管理员1");
        boolean result = sysRoleService.updateById(sysRole);
        System.out.println(result);
    }

    @Test
    //测试删除数据
    public void testDeleteById(){
        //只能对is_deleted=0进行修改
        boolean result = sysRoleService.removeById("2");
        System.out.println(result);
    }

    @Test
    //使用条件构造器 进行的查询测试
    public void testQueryWrapper() {
        QueryWrapper<SysRole> queryWrapper = new QueryWrapper<>();
        //判定条件为  role_code >= role
        queryWrapper.ge("role_code", "role");
        List<SysRole> users = sysRoleService.list(queryWrapper);
        System.out.println(users);
    }

    @Test
    //测试根据用户id查询出该用户所拥有的角色
    public void testGetRoleByUserId(){

    }
}

package org.example.system;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.example.model.system.SysMenu;
import org.example.system.service.SysMenuService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.Serializable;

/**
 * Created by 27 on 2023/10/2
 */
@SpringBootTest
public class SysMenuServiceTest {

    @Autowired
    SysMenuService sysMenuService;

    @Test
    public void testRemoveMenuById(){
        int id = 3;
        int count = sysMenuService.count(new QueryWrapper<SysMenu>().eq("parent_id", id));
        System.out.println("查找记录总数:"+count);
    }
}

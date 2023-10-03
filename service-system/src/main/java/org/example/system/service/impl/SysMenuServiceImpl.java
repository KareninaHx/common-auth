package org.example.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.common.result.ResultCodeEnum;
import org.example.model.system.SysMenu;
import org.example.model.system.SysRoleMenu;
import org.example.model.vo.AssginMenuVo;
import org.example.model.vo.RouterVo;
import org.example.system.exception.KareninaException;
import org.example.system.mapper.SysMenuMapper;
import org.example.system.mapper.SysRoleMenuMapper;
import org.example.system.service.SysMenuService;
import org.example.system.utils.MenuHelper;
import org.example.system.utils.RouterHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 27 on 2023/10/2
 */
@Transactional
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    @Autowired
    private SysMenuMapper sysMenuMapper;
    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;

    @Override
    public List<SysMenu> findNodes() {
        //全部权限列表
        List<SysMenu> sysMenuList = this.list(); // 这里的list()是查询所有数据，相当于select * from table
        if (CollectionUtils.isEmpty(sysMenuList)) return null; //使用集合工具的静态方法isEmpty判断是否有数据，如果没有数据则返回null 有则构建树形数据

        //构建树形数据
        List<SysMenu> result = MenuHelper.buildTree(sysMenuList);
        return result;
    }

    //删除菜单节点
    @Override
    public boolean removeById(Serializable id) {
        int count = this.count(new QueryWrapper<SysMenu>().eq("parent_id", id));
        if (count > 0) { //如果能查找到的记录大于0，则抛出异常 不允许删除
            throw new KareninaException(ResultCodeEnum.NODE_ERROR);
            // NODE_ERROR( 218, "该节点下有子节点，不可以删除")
        }
        sysMenuMapper.deleteById(id);
        return false; // 为什么返回为false
    }

    @Override
    public List<SysMenu> findSysMenuByRoleId(Long roleId) {
        //获取所有status为1的权限列表  ----由所有可知 使用selectList
        List<SysMenu> menuList = sysMenuMapper.selectList(new QueryWrapper<SysMenu>().eq("status", 1));
        //根据角色id获取角色权限     ----- 根据传入的参数roleId进行查找  也是查找所有
        List<SysRoleMenu> roleMenus = sysRoleMenuMapper.selectList(new QueryWrapper<SysRoleMenu>().eq("role_id",roleId));
        //获取该角色已分配的所有权限id   ------构建一个泛型为Long的列表来存放 权限id  roleMenuIds
        List<Long> roleMenuIds = new ArrayList<>();
        for (SysRoleMenu roleMenu : roleMenus) { //---------使用增强for循环 将id 放入roleMenuIds
            roleMenuIds.add(roleMenu.getMenuId());
        }
        //遍历所有权限列表------检查roleMenuIds是否包含了 menuList中sysmenu.getId()的值 不对，应该是sysMenu.getId是否包含在roleMenuIds
        for (SysMenu sysMenu : menuList) {
            if(roleMenuIds.contains(sysMenu.getId())){
                //设置该权限已被分配
                sysMenu.setSelect(true);
            }else {
                sysMenu.setSelect(false);
            }
        }
        //将权限列表转换为权限树
        List<SysMenu> sysMenus = MenuHelper.buildTree(menuList);
        return sysMenus;
    }

    @Override
    public void doAssign(AssginMenuVo assginMenuVo) {
//删除已分配的权限------防止重复 缺漏
        sysRoleMenuMapper.delete(new QueryWrapper<SysRoleMenu>().eq("role_id",assginMenuVo.getRoleId()));
        //遍历所有已选择的权限id------assginMenuVo为前端传入数据
        for (Long menuId : assginMenuVo.getMenuIdList()) {
            if(menuId != null){
                //创建SysRoleMenu对象------将当前 for循环 循环到的menuId依次给固定的roleId 角色赋予权限
                SysRoleMenu sysRoleMenu = new SysRoleMenu();
                sysRoleMenu.setMenuId(menuId);
                sysRoleMenu.setRoleId(assginMenuVo.getRoleId());
                //添加新权限-------一一添加 权限
                sysRoleMenuMapper.insert(sysRoleMenu);
            }
        }
    }

    @Override
    public List<RouterVo> findUserMenuList(Long userId) {
        //超级管理员admin账号id为：1
        List<SysMenu> sysMenuList = null;
        if (userId.longValue() == 1) {
            sysMenuList = sysMenuMapper.selectList(new QueryWrapper<SysMenu>().eq("status", 1).orderByAsc("sort_value"));
        } else {
            sysMenuList = sysMenuMapper.findListByUserId(userId);
        }
        //构建树形数据  ★
        List<SysMenu> sysMenuTreeList = MenuHelper.buildTree(sysMenuList);

        //构建路由   ★
        List<RouterVo> routerVoList = RouterHelper.buildRouters(sysMenuTreeList);
        return routerVoList;
    }

    @Override
    public List<String> findUserPermsList(Long userId) {
        //超级管理员admin账号id为：1
        List<SysMenu> sysMenuList = null;
        if (userId.longValue() == 1) {
            sysMenuList = sysMenuMapper.selectList(new QueryWrapper<SysMenu>().eq("status", 1));
        } else {
            // 按用户id查询都的菜单来分配 菜单列表
            sysMenuList = sysMenuMapper.findListByUserId(userId);
        }
        //创建返回的集合
        List<String> permissionList = new ArrayList<>();
        for (SysMenu sysMenu : sysMenuList) {
            if(sysMenu.getType() == 2){  //type为2表示按钮权限
                permissionList.add(sysMenu.getPerms());
            }
        }
        return permissionList;
    }
}

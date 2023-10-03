package org.example.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.model.system.SysMenu;
import org.example.model.vo.AssginMenuVo;
import org.example.model.vo.RouterVo;

import java.io.Serializable;
import java.util.List;

public interface SysMenuService extends IService<SysMenu> {

    /**
     * 菜单树形数据
     * @return
     */
    public List<SysMenu> findNodes();

    //删除菜单节点
    boolean removeById(Serializable id);

    /**
     * 根据角色获取授权权限数据
     * @return
     */
    List<SysMenu> findSysMenuByRoleId(Long roleId);


    /**
     * 保存角色权限
     * @param  assginMenuVo
     */
    void doAssign(AssginMenuVo assginMenuVo);

    /**
     * 获取用户菜单  页面权限
     * @param userId
     * @return
     */
    List<RouterVo> findUserMenuList(Long userId);

    /**
     * 获取用户按钮权限
     * @param userId
     * @return
     */
    List<String> findUserPermsList(Long userId);
}

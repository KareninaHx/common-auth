package org.example.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.model.system.SysUser;
import org.example.model.vo.RouterVo;
import org.example.model.vo.SysUserQueryVo;
import org.example.system.mapper.SysUserMapper;
import org.example.system.service.SysMenuService;
import org.example.system.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * Created by 27 on 2023/10/1
 */

@Transactional
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper,SysUser> implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private SysMenuService sysMenuService;

    @Override
    public IPage<SysUser> selectPage(Page<SysUser> pageParam, SysUserQueryVo userQueryVo){
        return sysUserMapper.selectPage(pageParam, userQueryVo);
    }

    @Override
    public void updateStatus(Long id, Integer status) {
        SysUser sysUser = sysUserMapper.selectById(id);
        sysUser.setStatus(status);
        sysUserMapper.updateById(sysUser);
    }

    @Override
    public SysUser getByUsername(String username) {
        return sysUserMapper.selectOne(new QueryWrapper<SysUser>().eq("username",username));
    }

    @Override
    public Map<String, Object> getUserInfo(String username) {
        Map<String, Object> result = new HashMap<>();
        SysUser sysUser = this.getByUsername(username);

        //根据用户id获取菜单权限值
        List<RouterVo> routerVoList = sysMenuService.findUserMenuList(sysUser.getId());
        //根据用户id获取用户按钮权限
        List<String> permsList = sysMenuService.findUserPermsList(sysUser.getId());

        result.put("name", sysUser.getName());
        result.put("avatar", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        //当前权限控制使用不到，我们暂时忽略
        result.put("roles",  new HashSet<>());
        result.put("buttons", permsList);
        result.put("routers", routerVoList);
        return result;
    }
}

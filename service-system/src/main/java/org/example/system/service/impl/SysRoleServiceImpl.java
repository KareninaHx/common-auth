package org.example.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.model.system.SysRole;
import org.example.model.system.SysUserRole;
import org.example.model.vo.AssginRoleVo;
import org.example.model.vo.SysRoleQueryVo;
import org.example.system.mapper.SysRoleMapper;
import org.example.system.mapper.SysUserRoleMapper;
import org.example.system.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 27 on 2023/9/30
 */
//com.baomidou.mybatisplus.extension.service.impl.ServiceImpl这是Mybatis-Plus提供的默认Service接口实现。
@Transactional
//↑？？
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    //条件分页查询
    @Override
    public IPage<SysRole> selectPage(Page<SysRole> pageParam, SysRoleQueryVo roleQueryVo) {
        return sysRoleMapper.selectPage(pageParam, roleQueryVo);
    }

    @Override
    //根据用户id获取角色信息
    public Map<String, Object> getRolesByUserId(Long userId) {
        //获取所有角色
        List<SysRole> roles = sysRoleMapper.selectList(null);
        //根据用户id查询
        QueryWrapper<SysUserRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",userId);
        //获取用户已分配的角色 ★
        List<SysUserRole> userRoles = sysUserRoleMapper.selectList(queryWrapper);
        //获取用户已分配的角色id
        List<Long> userRoleIds = new ArrayList<>();
        for (SysUserRole userRole : userRoles) {
            userRoleIds.add(userRole.getRoleId());//userRole 表示用户被分配了的角色+用户信息
        }
        //创建返回的Map
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("allRoles",roles);//所有角色
        returnMap.put("userRoleIds",userRoleIds);//用户相应 的角色+用户id信息
        return returnMap;
    }

    @Override
    public void doAssign(AssginRoleVo assginRoleVo) {
        //根据用户id删除原来分配的角色
        QueryWrapper<SysUserRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",assginRoleVo.getUserId());//assignRoleVo表示分配角色的信息
        sysUserRoleMapper.delete(queryWrapper);//避免重复（覆盖）
        //获取所有的角色id
        List<Long> roleIdList = assginRoleVo.getRoleIdList();
        for (Long roleId : roleIdList) {
            if(roleId != null){
                SysUserRole sysUserRole = new SysUserRole();
                sysUserRole.setUserId(assginRoleVo.getUserId());
                sysUserRole.setRoleId(roleId);
                //保存
                sysUserRoleMapper.insert(sysUserRole);
            }
        }
    }
}

package org.example.system.service.impl;

import org.example.model.system.SysUser;
import org.example.system.custom.CustomUser;
import org.example.system.service.SysMenuService;
import org.example.system.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by 27 on 2023/10/3
 */

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysMenuService sysMenuService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //根据用户名获取用户信息
        SysUser sysUser = sysUserService.getByUsername(username);
        if(null == sysUser) { //为空则为用户不存在
            throw new UsernameNotFoundException("用户名不存在！");
        }

        if(sysUser.getStatus().intValue() == 0) {//如果status为0则表示用户已被停用
            throw new RuntimeException("账号已停用");
        }
        //获取权限
        List<String> userPermsList = sysMenuService.findUserPermsList(sysUser.getId());
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (String perm : userPermsList) {
            authorities.add(new SimpleGrantedAuthority(perm.trim()));
        }
        return new CustomUser(sysUser, authorities);
    }
}

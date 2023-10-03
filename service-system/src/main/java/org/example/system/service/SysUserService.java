package org.example.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.example.model.system.SysUser;
import org.example.model.vo.SysUserQueryVo;

import java.util.Map;

public interface SysUserService extends IService<SysUser> {

    //用于实现分页
    IPage<SysUser> selectPage(Page<SysUser> pageParam, SysUserQueryVo adminQueryVo);

//    更新用户状态
    void updateStatus(Long id, Integer status);

//    根据用户名查找信息
    SysUser getByUsername(String username);

    /**
     * 根据用户名获取用户登录信息
     * @param username
     * @return
     */
    Map<String, Object> getUserInfo(String username);
}

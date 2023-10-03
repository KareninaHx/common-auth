package org.example.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.model.system.SysLoginLog;
import org.example.model.vo.SysLoginLogQueryVo;

public interface SysLoginLogService extends IService<SysLoginLog> {

    //列表显示
    IPage<SysLoginLog> selectPage(Page<SysLoginLog> pageParam, SysLoginLogQueryVo sysLoginLogQueryVo);
}

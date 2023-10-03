package org.example.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.example.model.system.SysOperLog;
import org.example.model.vo.SysOperLogQueryVo;

public interface SysOperLogService extends IService<SysOperLog> {
    //操作日志分页查询
    IPage<SysOperLog> selectPage(Long page, Long limit, SysOperLogQueryVo sysOperLogQueryVo);

    void saveSysLog(SysOperLog sysOperLog);
}

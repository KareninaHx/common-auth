package org.example.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.model.system.SysOperLog;
import org.example.model.vo.SysOperLogQueryVo;
import org.example.system.mapper.SysOperLogMapper;
import org.example.system.service.SysOperLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * Created by 27 on 2023/10/3
 */
@Service
public class AsyncOperLogServiceImpl extends ServiceImpl<SysOperLogMapper,SysOperLog> implements SysOperLogService {

    @Autowired
    private SysOperLogMapper operLogMapper;
    //操作日志分页查询
    @Override
    public IPage<SysOperLog> selectPage(Long page, Long limit, SysOperLogQueryVo sysOperLogQueryVo) {
        Page<SysOperLog> pageParam = new Page<>(page,limit);
        //获取条件值
        String title = sysOperLogQueryVo.getTitle();
        String operName = sysOperLogQueryVo.getOperName();
        String createTimeBegin = sysOperLogQueryVo.getCreateTimeBegin();
        String createTimeEnd = sysOperLogQueryVo.getCreateTimeEnd();
        //封装参数
        QueryWrapper<SysOperLog> wrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(title)) {
            wrapper.like("title",title);
        }
        if(!StringUtils.isEmpty(operName)) {
            wrapper.like("oper_name",operName);
        }
        if(!StringUtils.isEmpty(createTimeBegin)) {
            wrapper.ge("create_time",createTimeBegin);
        }
        if(!StringUtils.isEmpty(createTimeEnd)) {
            wrapper.le("create_time",createTimeEnd);
        }
        //调用mapper方法实现分页条件查询
        IPage<SysOperLog> sysOperLogPage = operLogMapper.selectPage(pageParam, wrapper);
        return sysOperLogPage;
    }

    @Async
    @Override
    public void saveSysLog(SysOperLog sysOperLog) {
        operLogMapper.insert(sysOperLog);
    }
}

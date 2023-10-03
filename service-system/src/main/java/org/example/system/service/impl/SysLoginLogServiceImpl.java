package org.example.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.model.system.SysLoginLog;
import org.example.model.vo.SysLoginLogQueryVo;
import org.example.system.mapper.SysLoginLogMapper;
import org.example.system.service.SysLoginLogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by 27 on 2023/10/3
 */

@Service
@SuppressWarnings({"unchecked", "rawtypes"})
public class SysLoginLogServiceImpl extends ServiceImpl<SysLoginLogMapper,SysLoginLog> implements SysLoginLogService{

    @Resource
    private SysLoginLogMapper sysLoginLogMapper;

    @Override
    public IPage<SysLoginLog> selectPage(Page<SysLoginLog> pageParam, SysLoginLogQueryVo sysLoginLogQueryVo) {
        return sysLoginLogMapper.selectPage(pageParam, sysLoginLogQueryVo);
    }
}

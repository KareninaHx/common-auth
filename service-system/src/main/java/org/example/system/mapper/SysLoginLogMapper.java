package org.example.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.example.model.system.SysLoginLog;
import org.example.model.vo.SysLoginLogQueryVo;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface SysLoginLogMapper extends BaseMapper<SysLoginLog> {
    IPage<SysLoginLog> selectPage(Page<SysLoginLog> page, @Param("vo") SysLoginLogQueryVo sysLoginLogQueryVo);
}

package org.example.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.example.model.system.SysOperLog;
import org.example.model.vo.SysOperLogQueryVo;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface SysOperLogMapper extends BaseMapper<SysOperLog> {
    IPage<SysOperLog> selectPage(Page<SysOperLog> page, @Param("vo") SysOperLogQueryVo sysOperLogQueryVo);
}

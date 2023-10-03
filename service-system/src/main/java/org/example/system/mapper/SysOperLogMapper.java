package org.example.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import org.apache.ibatis.annotations.Mapper;

import org.example.model.system.SysOperLog;

import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface SysOperLogMapper extends BaseMapper<SysOperLog> {

}

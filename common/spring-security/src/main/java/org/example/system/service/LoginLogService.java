package org.example.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.example.model.system.SysLoginLog;
import org.example.model.vo.SysLoginLogQueryVo;

/**
 * 异步调用日志服务
 */
public interface LoginLogService {
    /**
     * 记录登录信息
     *
     * @param username 用户名
     * @param status 状态
     * @param ipaddr ip
     * @param message 消息内容
     * @return
     */
    void recordLoginLog(String username, Integer status, String ipaddr, String message);

    //条件分页查询登录日志
    IPage<SysLoginLog> selectPage(long page, long limit, SysLoginLogQueryVo sysLoginLogQueryVo);
}

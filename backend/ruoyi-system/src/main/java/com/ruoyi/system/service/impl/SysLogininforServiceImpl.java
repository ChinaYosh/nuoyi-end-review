package com.ruoyi.system.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.domain.SysLogininfor;
import com.ruoyi.system.mapper.SysLogininforMapper;
import com.ruoyi.system.service.ISysLogininforService;
import lombok.RequiredArgsConstructor;

/**
 * 系统访问日志情况信息 服务层处理
 * 
 * @author ruoyi
 */
@Service
@RequiredArgsConstructor
public class SysLogininforServiceImpl implements ISysLogininforService
{

    private final SysLogininforMapper logininforMapper;

    /**
     * 新增系统登录日志
     * 
     * @param logininfor 访问日志对象
     */
    @Override
    public void insertLogininfor(SysLogininfor logininfor)
    {
        logininforMapper.insert(logininfor);
    }

    /**
     * 查询系统登录日志集合
     * 
     * @param logininfor 访问日志对象
     * @return 登录记录集合
     */
    @Override
    public List<SysLogininfor> selectLogininforList(SysLogininfor logininfor)
    {
        LambdaQueryWrapper<SysLogininfor> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.isNotEmpty(logininfor.getUserName()), SysLogininfor::getUserName, logininfor.getUserName())
               .eq(StringUtils.isNotEmpty(logininfor.getStatus()), SysLogininfor::getStatus, logininfor.getStatus())
               .like(StringUtils.isNotEmpty(logininfor.getIpaddr()), SysLogininfor::getIpaddr, logininfor.getIpaddr())
               .orderByDesc(SysLogininfor::getLoginTime);
        // 时间范围查询
        Map<String, Object> params = logininfor.getParams();
        if (params != null && params.containsKey("beginTime"))
        {
            wrapper.apply("date_format(login_time,'%Y%m%d') >= date_format({0},'%Y%m%d')", params.get("beginTime"));
        }
        if (params != null && params.containsKey("endTime"))
        {
            wrapper.apply("date_format(login_time,'%Y%m%d') <= date_format({0},'%Y%m%d')", params.get("endTime"));
        }
        return logininforMapper.selectList(wrapper);
    }

    /**
     * 批量删除系统登录日志
     * 
     * @param infoIds 需要删除的登录日志ID
     * @return 结果
     */
    @Override
    public int deleteLogininforByIds(Long[] infoIds)
    {
        return logininforMapper.deleteByIds(Arrays.asList(infoIds));
    }

    /**
     * 清空系统登录日志
     */
    @Override
    public void cleanLogininfor()
    {
        logininforMapper.delete(new LambdaQueryWrapper<>());
    }
}

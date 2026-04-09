package com.ruoyi.quartz.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component("MyTask")
@Slf4j
public class MyTask
{
    public void run()
    {
        log.info("执行Mytask的run方法");
    }
}

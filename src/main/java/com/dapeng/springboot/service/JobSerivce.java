package com.dapeng.springboot.service;

import com.dapeng.springboot.dto.JobDto;
import com.dapeng.springboot.jpa.JobDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author lipeng
 * @version 1.0
 * @date 2020/3/16 12:53
 * @message：服务内容
 */
@Slf4j
@Service
public class JobSerivce {


    @Resource
    private JobDao jobDao;

    /**
     * 保存需求，任务缺陷迭代需求任务
     * @return
     */
    public Boolean saveJob(JobDto jobDto){

        return null;
    }
}

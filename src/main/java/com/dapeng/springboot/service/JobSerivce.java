package com.dapeng.springboot.service;

import com.dapeng.springboot.dto.JobDto;
import com.dapeng.springboot.dto.ProjectInfoDto;
import com.dapeng.springboot.dto.UserInfoDto;
import com.dapeng.springboot.entity.JobEntity;
import com.dapeng.springboot.jpa.JobDao;
import com.dapeng.springboot.jpa.ProjectDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private ProjectService projectService;

    /**
     * 保存需求，任务缺陷迭代需求任务
     * @return
     */
    public JobDto saveJob(JobDto jobDto){
        JobEntity entity = new JobEntity();
        BeanUtils.copyProperties(jobDto,entity);
        UserInfoDto handler = getUserInfo(jobDto.getHandlerId());
        entity.setHandlerName(handler.getUserName());
        entity.setProjectName(getByProjectId(jobDto.getProjectId()).getProjectName());
        jobDao.save(entity);
        jobDto.setId(entity.getId());
        return jobDto;
    }

    private UserInfoDto getUserInfo(Long userId){
        return userInfoService.findById(userId);
    }

    private ProjectInfoDto getByProjectId(Long projectId){
        return projectService.findById(projectId);
    }

    //查询迭代信息 根据id

}

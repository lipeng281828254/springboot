package com.dapeng.springboot.service;

import com.dapeng.springboot.dto.ProjectInfoDto;
import com.dapeng.springboot.entity.ProjectEntity;
import com.dapeng.springboot.entity.ProjectUserReltiveEntity;
import com.dapeng.springboot.jpa.ProjectDao;
import com.dapeng.springboot.jpa.ProjectUserDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author lipeng
 * @version 1.0
 * @date 2020/3/11 22:55
 * @message：项目service
 */
@Slf4j
@Service
public class ProjectService {

    @Resource
    private ProjectDao projectDao;
    @Resource
    private ProjectUserDao projectUserDao;

    /**
     * 创建项目
     *
     * @param projectInfoDto
     * @return
     */
    @Transactional
    public ProjectInfoDto createProject(ProjectInfoDto projectInfoDto) {
        ProjectEntity entity = new ProjectEntity();
        BeanUtils.copyProperties(projectInfoDto,entity);
        entity.setStatus("进行中");//默认
        ProjectEntity result = projectDao.save(entity);
        projectInfoDto.setId(result.getId());
        //创建项目关系
        ProjectUserReltiveEntity reltiveEntity = new ProjectUserReltiveEntity();
        reltiveEntity.setProjectId(projectInfoDto.getId());
        reltiveEntity.setUserId(projectInfoDto.getCreateBy());
        reltiveEntity.setUserName(projectInfoDto.getCreateName());
        reltiveEntity.setProjectRole("超级管理员");
        projectUserDao.save(reltiveEntity);
        return projectInfoDto;
    }


}

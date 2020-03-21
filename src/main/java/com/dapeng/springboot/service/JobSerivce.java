package com.dapeng.springboot.service;

import com.dapeng.springboot.dto.JobDto;
import com.dapeng.springboot.dto.NoticeDto;
import com.dapeng.springboot.dto.ProjectInfoDto;
import com.dapeng.springboot.dto.UserInfoDto;
import com.dapeng.springboot.entity.JobEntity;
import com.dapeng.springboot.jpa.JobDao;
import com.dapeng.springboot.jpa.ProjectDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
     *
     * @return
     */
    public JobDto saveJob(JobDto jobDto) {
        JobEntity entity = new JobEntity();
        BeanUtils.copyProperties(jobDto, entity);
        UserInfoDto handler = getUserInfo(jobDto.getHandlerId());
        entity.setHandlerName(handler.getUserName());
        entity.setProjectName(getByProjectId(jobDto.getProjectId()).getProjectName());
        jobDao.save(entity);
        jobDto.setId(entity.getId());
        return jobDto;
    }

    private UserInfoDto getUserInfo(Long userId) {
        return userInfoService.findById(userId);
    }

    private ProjectInfoDto getByProjectId(Long projectId) {
        return projectService.findById(projectId);
    }

    //根据id删除关系
    public boolean deleteRelation(Long id) {
        int count = jobDao.deleteRelation(id);
        return count == 1;
    }

    //根据需求id和job类型查询列表
    public List<JobDto> listJobByDemindAndType(Long demandId, String type) {
        log.info("根据需求id和类型查询列表,--{}--{}",demandId,type);
        List<JobEntity> entitys = jobDao.findByDemandIdAndType(demandId, type);
        if (entitys != null && entitys.size() > 0) {
            List<JobDto> jobDtos = new ArrayList<>();
            entitys.forEach(jobEntity -> {
                JobDto jobDto = new JobDto();
                BeanUtils.copyProperties(jobEntity,jobDto);
                jobDtos.add(jobDto);
            });
            return jobDtos;
        }
        return null;
    }

    /**
     * 根据id删除
     */
    public boolean deleteById(Long id){
        jobDao.deleteById(id);
        return true;
    }

    @Transactional
    //更新job内容，注意生成消息的变化
    public boolean updateJob(JobDto jobDto,UserInfoDto userInfoDto) {
        log.info("更新job入参--->>>{}",jobDto);
        //更新基本信息
        if (jobDto.getId() == null){
            throw new RuntimeException("id不能为空");
        }
        JobEntity entity = jobDao.getOne(jobDto.getId());
        BeanUtils.copyProperties(jobDto,entity);
        jobDao.save(entity);
        log.info("更新job完成");
        log.info("创建消息通知信息");
        //附件上传生成消息
        if (jobDto.getFileId() != null){
            log.info("上传附件信息创建通知");

        }
        //状态变化生成消息
        return false;
    }

    //生成附件上传通知消息
    private void createNoticeFile(JobEntity entity,JobDto jobDto,UserInfoDto userInfoDto){
        //创建人user 生成两条，通知Job创建人和处理者
        Long createBy = userInfoDto.getId();
        String createName = userInfoDto.getUserName();

        NoticeDto noticeDto = new NoticeDto();
        noticeDto.setProjectId(entity.getProjectId());
        noticeDto.setJobTitle(entity.getTitle());
        noticeDto.setCreateBy(userInfoDto.getId());
        noticeDto.setCreateName(userInfoDto.getUserName());
        noticeDto.setCreateTime(new Date());

    }
}

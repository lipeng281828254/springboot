package com.dapeng.springboot.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.dapeng.springboot.dto.*;
import com.dapeng.springboot.entity.JobEntity;
import com.dapeng.springboot.jpa.JobDao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

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
    @Autowired
    private NoticeService noticeService;

    /**
     * 保存需求，任务缺陷迭代需求任务
     *
     * @return
     */
    public JobDto saveJob(JobDto jobDto) {
        jobDto.checkDd();
        JobEntity entity = new JobEntity();
        BeanUtils.copyProperties(jobDto, entity);
        if (!"迭代".equals(jobDto.getType())){
            UserInfoDto handler = getUserInfo(jobDto.getHandlerId());
            entity.setHandlerName(handler.getUserName());
        }
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
        log.info("根据需求id和类型查询列表,--{}--{}", demandId, type);
        List<JobEntity> entitys = jobDao.findByDemandIdAndType(demandId, type);
        if (entitys != null && entitys.size() > 0) {
            List<JobDto> jobDtos = new ArrayList<>();
            entitys.forEach(jobEntity -> {
                JobDto jobDto = new JobDto();
                BeanUtils.copyProperties(jobEntity, jobDto);
                jobDtos.add(jobDto);
            });
            return jobDtos;
        }
        return null;
    }

    /**
     * 根据id删除
     */
    public boolean deleteById(Long id) {
        jobDao.deleteById(id);
        return true;
    }

    @Transactional
    //更新job内容，注意生成消息的变化
    public boolean updateJob(JobDto jobDto, UserInfoDto userInfoDto) {
        log.info("更新job入参--->>>{}", jobDto);
        //更新基本信息
        if (jobDto.getId() == null) {
            throw new RuntimeException("id不能为空");
        }
        JobEntity entity = jobDao.getOne(jobDto.getId());

        log.info("创建消息通知信息开始---");
        //附件上传生成消息
        String name = StringUtils.isEmpty(userInfoDto.getUserName()) ? userInfoDto.getId().toString() : userInfoDto.getUserName();
        if (!StringUtils.isEmpty(jobDto.getFileId()) && !jobDto.getFileId().equals(entity.getFileId())) {
            log.info("上传附件信息创建通知");
            String content = "用户".concat(name).concat("上传了文件").concat(jobDto.getFileName());
            createNotice(entity, jobDto, userInfoDto, "上传附件通知", content);
        }
        //状态变化生成消息
        if (!StringUtils.isEmpty(jobDto.getStatus()) && !jobDto.getStatus().equals(entity.getStatus())) {
            log.info("修改状态信息创建通知");
            String content = "用户".concat(name).concat("将属性'状态'修改为").concat(jobDto.getStatus());
            createNotice(entity, jobDto, userInfoDto, "上传附件通知", content);
        }
        //修改处理人
        if (!StringUtils.isEmpty(jobDto.getHandlerId()) && !jobDto.getHandlerId().equals(entity.getHandlerId())) {
            log.info("修改处理人信息创建通知");
            UserInfoDto dto = userInfoService.getById(jobDto.getHandlerId());
            jobDto.setHandlerName(dto.getUserName());
            entity.setHandlerId(jobDto.getHandlerId());
            entity.setHandlerName(jobDto.getHandlerName());
            String content = "用户".concat(name).concat("将属性'处理人'修改为").concat(jobDto.getHandlerName());
            createNotice(entity, jobDto, userInfoDto, "属性变化通知", content);
        }
        log.info("创建消息通知信息结束---");
//        BeanUtils.copyProperties(jobDto, entity);
        BeanUtil.copyProperties(jobDto, entity, CopyOptions.create().setIgnoreNullValue(true).setIgnoreError(true));
        log.info("复制后结果：--->>>{}", entity);
        jobDao.save(entity);
        log.info("更新job完成---->>>");
        return false;
    }

    //生成通知消息
    private void createNotice(JobEntity entity, JobDto jobDto, UserInfoDto userInfoDto, String action, String content) {
        //创建人user 生成两条，通知Job创建人和处理者--消息的处理人
        Long createBy = userInfoDto.getId();
        String createName = userInfoDto.getUserName();
        ProjectInfoDto projectDto = projectService.findById(entity.getProjectId());
        NoticeDto noticeDto = new NoticeDto();
        noticeDto.setCreateBy(createBy);
        noticeDto.setCreateName(createName);
        noticeDto.setProjectId(entity.getProjectId());
        noticeDto.setProjectName(projectDto.getProjectName());
        noticeDto.setJobTitle(StringUtils.isEmpty(jobDto.getTitle()) ? entity.getTitle() : jobDto.getTitle());
        noticeDto.setJobType(StringUtils.isEmpty(jobDto.getTitle()) ? entity.getType() : jobDto.getType());
        noticeDto.setAction(action);
        noticeDto.setCreateBy(userInfoDto.getId());
        noticeDto.setCreateName(userInfoDto.getUserName());
        noticeDto.setCreateTime(new Date());
        noticeDto.setContent(content);
        noticeDto.setStatus("未读");
        //处理人，Job创建者
        if (!noticeDto.getCreateBy().equals(jobDto.getCreateBy())) {
            noticeDto.setHandlerId(entity.getCreateBy());
            noticeDto.setHandlerName(entity.getCreateName());
            //只需要看到
            noticeService.createNotice(noticeDto);
        }
        if (!noticeDto.getCreateBy().equals(jobDto.getHandlerId())) {
            //创建处理者接收消息
            noticeDto.setHandlerId(entity.getHandlerId());
            noticeDto.setHandlerName(entity.getHandlerName());
            noticeService.createNotice(noticeDto);
        }
    }

    /**
     * 根据id查询详情
     * * @param id
     *
     * @return
     */
    public JobDto getById(Long id) {
        JobEntity entity = jobDao.getOne(id);
        if (entity == null) {
            return null;
        }
        JobDto jobDto = new JobDto();
        BeanUtils.copyProperties(entity, jobDto);
        return jobDto;
    }

    /**
     * 查询代办列表
     *
     * @param handlerId
     * @return
     */
    public List<JobDto> queryByHandleId(Long projectId, String type, Long handlerId) {
        List<JobEntity> entits = jobDao.findByProjectIdAndTypeAndHandlerId(projectId, type, handlerId);
        if (entits == null || entits.size() < 1) {
            return new ArrayList<>();
        }
        List<JobDto> jobDtos = new ArrayList<>();
        entits.forEach(jobEntity -> {
            JobDto jobDto = new JobDto();
            BeanUtils.copyProperties(jobEntity, jobDto);
            jobDtos.add(jobDto);
        });
        return jobDtos;
    }

    /**
     * 条件查询
     *
     * @param jobDto
     * @return
     */
    public List<JobDto> queryJobByConditons(JobDto jobDto) {
        JobEntity entity = new JobEntity();
        BeanUtils.copyProperties(jobDto, entity);
        Example<JobEntity> example1 = Example.of(entity);
        List<JobEntity> entities = jobDao.findAll(example1);
        if (entities == null || entities.size() < 1) {
            return null;
        }
        List<JobDto> jobDtos = new ArrayList<>();
        entities.forEach(jobEntity -> {
            JobDto jobDto1 = new JobDto();
            BeanUtils.copyProperties(jobEntity, jobDto1);
            jobDtos.add(jobDto1);
        });
        return jobDtos;
    }


    public List<JobDto> queryMyCreate(Long projectId, String type, Long createBy) {
        List<JobEntity> entits = jobDao.findByProjectIdAndTypeAndCreateBy(projectId, type, createBy);
        if (entits == null || entits.size() < 1) {
            return new ArrayList<>();
        }
        List<JobDto> jobDtos = new ArrayList<>();
        entits.forEach(jobEntity -> {
            JobDto jobDto = new JobDto();
            BeanUtils.copyProperties(jobEntity, jobDto);
            jobDtos.add(jobDto);
        });
        return jobDtos;
    }

    //根据类型查询列表
    public List<JobDto> listJobByPidAndType(Long projectId, String type) {
        List<JobEntity> entits = jobDao.findByProjectIdAndType(projectId, type);
        if (entits == null || entits.size() < 1) {
            return new ArrayList<>();
        }
        List<JobDto> jobDtos = new ArrayList<>();
        entits.forEach(jobEntity -> {
            JobDto jobDto = new JobDto();
            BeanUtils.copyProperties(jobEntity, jobDto);
            jobDtos.add(jobDto);
        });
        return jobDtos;
    }

    //根据迭代id统计
    public IterationStatisticDto statisticJob(Long jobId) {
        List<JobEntity> xuqiu = jobDao.findByIterationIdAndType(jobId, "需求");
        List<JobEntity> task = jobDao.findByIterationIdAndType(jobId, "任务");
        List<JobEntity> defect = jobDao.findByIterationIdAndType(jobId, "缺陷");
        IterationStatisticDto statisticDto = new IterationStatisticDto();
        if (xuqiu == null || xuqiu.size() < 1) {
            statisticDto.setDemandCount(0);
        } else {
            statisticDto.setDemandCount(xuqiu.size());
        }
        if (task == null || task.size() < 1) {
            statisticDto.setTaskCount(0);
        } else {
            statisticDto.setTaskCount(task.size());
        }
        if (defect == null || defect.size() < 1) {
            statisticDto.setDefectCount(0);
        } else {
            statisticDto.setDefectCount(defect.size());
        }
        statisticDto.setTaskCount(statisticDto.getDefectCount() + statisticDto.getTaskCount() + statisticDto.getDemandCount());
        return statisticDto;
    }

    //查询迭代下列表
    public List<IterationStatisticDto> queryByIteration(Long jobId) {
        //查询当前迭代人员所有的处理人
        List<Long> handlers = jobDao.queryHandlers(jobId);
        if (handlers == null || handlers.size()<1){
            return new ArrayList<>();
        }
        List<IterationStatisticDto> statisticDtos = new ArrayList<>();
        handlers.forEach(handlerId ->{
            IterationStatisticDto xuqiu = new IterationStatisticDto();
            statistic(jobId,"需求",xuqiu);
            statisticDtos.add(xuqiu);
            IterationStatisticDto task = new IterationStatisticDto();
            statistic(jobId,"任务",task);
            statisticDtos.add(task);
            IterationStatisticDto quexian = new IterationStatisticDto();
            statistic(jobId,"缺陷",quexian);
            statisticDtos.add(quexian);
        });
        return statisticDtos;
    }

    //查询按裂隙
    private IterationStatisticDto statistic(Long jobId,String type,IterationStatisticDto iterationStatisticDto){
        List<JobEntity> entities = jobDao.findByIterationIdAndType(jobId,type);
        if (entities == null || entities.size() < 1){
            iterationStatisticDto.setDemandCount(0);
            iterationStatisticDto.setJobDtos(new ArrayList<>());
        } else {
            iterationStatisticDto.setHandlerName(entities.get(0).getHandlerName());
            iterationStatisticDto.setDemandCount(entities.size());
            iterationStatisticDto.setJobDtos(build(entities));
        }
        return iterationStatisticDto;
    }



    private List<JobDto> build(List<JobEntity> entities){
        List<JobDto> jobDtos = new ArrayList<>();
        entities.forEach(entity ->{
            JobDto jobDto = new JobDto();
            BeanUtils.copyProperties(entity,jobDto);
            jobDtos.add(jobDto);
        });
        return jobDtos;
    }

    //根据迭代id查询下面所有列表，任务需求，缺陷
    public List<JobDto> queryAllByIteration(Long jobId) {
        List<JobEntity> entities = jobDao.findByIterationId(jobId);
        if (entities == null || entities.size()<1){
            return null;
        }
        List<JobDto> jobDtos = new ArrayList<>();
        entities.forEach(entity->{
            JobDto jp = new JobDto();
            BeanUtils.copyProperties(entity,jp);
            jobDtos.add(jp);
        });
        return jobDtos;
    }
}

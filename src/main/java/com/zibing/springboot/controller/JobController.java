package com.zibing.springboot.controller;

import com.zibing.springboot.dto.IterationStatisticDto;
import com.zibing.springboot.dto.JobDto;
import com.zibing.springboot.dto.UserInfoDto;
import com.zibing.springboot.service.JobSerivce;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

/**
 * @author zibing
 * @version 1.0
 * @date 2020/3/17 0:04
 * @message：
 */
@Slf4j
@RestController
@RequestMapping("api/job/")
public class JobController {


    @Autowired
    private JobSerivce jobSerivce;

    @PostMapping("createJob.json")
    public JobDto createJob(@Valid @RequestBody JobDto jobDto, HttpServletRequest request) {
        HttpSession session = request.getSession();
        UserInfoDto createInfo = (UserInfoDto) session.getAttribute("userInfo");
        jobDto.setCreateBy(createInfo.getId());
        jobDto.setCreateName(createInfo.getUserName());
//        jobDto.setCreateBy(11l);
//        jobDto.setCreateName("小明");
        return jobSerivce.saveJob(jobDto);
    }

    //解除关联关系
    @GetMapping("deleteRelation.json")
    public boolean deleteRelation(Long id) {
        return jobSerivce.deleteRelation(id);
    }

    @GetMapping("listJobByDemindAndType.json")
    public List<JobDto> listJobs(Long demindId, String type) {
        return jobSerivce.listJobByDemindAndType(demindId, type);
    }

    //根据id删除
    @PostMapping("deleteById.json")
    public boolean deleteById(@RequestBody JobDto jobDto) {
        log.info("id = {}",jobDto.getId());
        return jobSerivce.deleteById(jobDto.getId());
    }

    //更新jOb信息，状态变化生成消息
    @PostMapping("updateJob.json")
    public boolean updateJob(@RequestBody JobDto jobDto, HttpServletRequest request) {
        HttpSession session = request.getSession();
        UserInfoDto userInfoDto = (UserInfoDto) session.getAttribute("userInfo");
        return jobSerivce.updateJob(jobDto, userInfoDto);
    }

    @GetMapping("getById.json")
    public JobDto getById(Long id) {
        return jobSerivce.getById(id);
    }

    /**
     * 查询代办
     * @param jobDto
     * @param request
     * @param request
     * @return
     */
    @GetMapping("queryNeedToDealt.json")
    public List<JobDto> queryNeedToDetail(JobDto jobDto,HttpServletRequest request) {
        HttpSession session = request.getSession();
        UserInfoDto userInfoDto = (UserInfoDto) session.getAttribute("userInfo");
        jobDto.setHandlerId(userInfoDto.getId());
        return jobSerivce.queryByHandleId(jobDto);
    }

    //我创建的
    @GetMapping("queryMycreate.json")
    public List<JobDto> queryMycreate(JobDto jobDto,HttpServletRequest request){
        HttpSession session = request.getSession();
        UserInfoDto userInfoDto = (UserInfoDto) session.getAttribute("userInfo");
        jobDto.setCreateBy(userInfoDto.getId());
        return jobSerivce.queryMyCreate(jobDto);
    }

    @GetMapping("queryJob.json")
    public List<JobDto> queryByContions(JobDto jobDto) {
        return jobSerivce.queryJobByConditons(jobDto);
    }

    /**
     * 根据类型查询项目下的工作项
     */
    @GetMapping("listJobByType.json")
    public List<JobDto> listJobsByPid(Long projectId,String type){
        return jobSerivce.listJobByPidAndType(projectId,type);
    }

    /**
     * 迭代统计信息
     */
    @GetMapping("statisticIteration.json")
    public IterationStatisticDto statisticById(Long jobId){
        return jobSerivce.statisticJob(jobId);
    }

    /**
     * 查询迭代下详情
     */
    @GetMapping("queryByIteration.json")
    public List<IterationStatisticDto> queryByInteration(Long jobId){

        return jobSerivce.queryByIteration(jobId);
    }

    @GetMapping("queryAllByIterationId")
    public List<JobDto> queryByIterationId(Long jobId){
        return jobSerivce.queryAllByIteration(jobId);
    }
}
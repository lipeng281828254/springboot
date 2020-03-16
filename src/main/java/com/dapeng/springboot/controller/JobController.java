package com.dapeng.springboot.controller;

import com.dapeng.springboot.dto.JobDto;
import com.dapeng.springboot.dto.UserInfoDto;
import com.dapeng.springboot.service.JobSerivce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * @author lipeng
 * @version 1.0
 * @date 2020/3/17 0:04
 * @message：
 */
@RestController
@RequestMapping("/job/")
public class JobController {


    @Autowired
    private JobSerivce jobSerivce;

    @PostMapping("createJob.json")
    public JobDto createJob(@Valid @RequestBody JobDto jobDto, HttpServletRequest request){
       HttpSession session = request.getSession();
       UserInfoDto createInfo = (UserInfoDto) session.getAttribute("userInfo");
       jobDto.setCreateBy(createInfo.getId());
       jobDto.setCreateName(createInfo.getUserName());
//        jobDto.setCreateBy(11l);
//        jobDto.setCreateName("小明");
       return jobSerivce.saveJob(jobDto);
    }
}

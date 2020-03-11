package com.dapeng.springboot.controller;

import com.dapeng.springboot.dto.ProjectInfoDto;
import com.dapeng.springboot.dto.UserInfoDto;
import com.dapeng.springboot.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * @author lipeng
 * @version 1.0
 * @date 2020/3/11 23:03
 * @message：项目控制层
 */
@RestController
@RequestMapping("/project/")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @PostMapping("createProject.json")
    public ProjectInfoDto create(@Valid @RequestBody ProjectInfoDto projectInfoDto, HttpServletRequest request){
        HttpSession session = request.getSession();
        UserInfoDto userInfo = (UserInfoDto) session.getAttribute("userInfo");
        if (userInfo == null){
            throw new RuntimeException("session过期");
        }
        projectInfoDto.setCreateBy(userInfo.getId());
        projectInfoDto.setCreateName(userInfo.getUserName());
//        projectInfoDto.setCreateBy(11l);
//        projectInfoDto.setCreateName("小明");
        if (!userInfo.getUserType().equals("02")){
            throw new RuntimeException("只有团队负责人才能创建项目");
        }
        return projectService.createProject(projectInfoDto);
    }
}

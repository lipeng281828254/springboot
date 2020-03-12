package com.dapeng.springboot.controller;

import com.dapeng.springboot.dto.ProjectInfoDto;
import com.dapeng.springboot.dto.UserInfoDto;
import com.dapeng.springboot.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

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

    /**
     * 团队负责人创建项目
     * @param projectInfoDto
     * @param request
     * @return
     */
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

    /**
     * 查询成员所在项目列表（当前登录者）
     * @return
     */
    @GetMapping("lisetProject.json")
    public List<ProjectInfoDto> listProject(Long userId){
        return projectService.listProjectByUserId(userId);
    }

    /**
     * 查询项目下所有的成员
     * @return
     */
    @GetMapping("listProjectUsers.json")
    public List<UserInfoDto> listProjectUsers(Long projectId){
        return projectService.queryUsersByProject(projectId);
    }

    //更新权限
    @PostMapping("updateRoleByUserIdAndProjectId.json")
    public Boolean updateRoleByPidAndUid(@Param("userId") Long userId, @Param("projectId") Long projectId, @Param("projectRole") String projectRole){
        return projectService.updateUserRole(userId,projectId,projectRole);
    }

    //更新项目
    @PostMapping("updateProject.json")
    public Boolean updateProjectById(@Param("id") Long id,@Param("projectName") String projectName,@Param("descript") String descript){
        return projectService.updateProjectById(id,projectName,descript);
    }

    //邀请加入项目
    @GetMapping("inviteToProject.json")
    public Boolean inviteToProject(){
        return null;
    }

    //回复加入项目
}

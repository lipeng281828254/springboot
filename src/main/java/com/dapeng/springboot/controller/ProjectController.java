package com.dapeng.springboot.controller;

import com.dapeng.springboot.dto.InviteProjectDto;
import com.dapeng.springboot.dto.ProjectInfoDto;
import com.dapeng.springboot.dto.ReplyDto;
import com.dapeng.springboot.dto.UserInfoDto;
import com.dapeng.springboot.service.ProjectService;
import com.dapeng.springboot.service.UserInfoService;
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
@RequestMapping("api/project/")
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
     * 查询成员所在项目列表（当前登录者）  所在项目
     * @return
     */
    @GetMapping("lisetProject.json")
    public List<ProjectInfoDto> listProject(HttpServletRequest request){
        HttpSession session = request.getSession();
        UserInfoDto user = (UserInfoDto) session.getAttribute("userInfo");
        return projectService.listProjectByUserId(user.getId());
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

//    @Autowired
//    private UserInfoService user;
    //邀请加入项目  ..如果属于其他团队，不能加入，如果不属于，同意后，先加入团队，再加入项目。如果是该团队成员，直接加入项目。
    @PostMapping("inviteToProject.json")
    public Boolean inviteToProject(@Valid @RequestBody InviteProjectDto inviteProjectDto,HttpServletRequest request){
        HttpSession session = request.getSession();
        UserInfoDto userInfo = (UserInfoDto) session.getAttribute("userInfo");
        Long createId = userInfo.getId();//获取团队负责人id
//        UserInfoDto userInfo = user.getByLoginName("321134223@qq.com");
        inviteProjectDto.setCreateBy(createId);
        return projectService.inviteIntoProject(inviteProjectDto,userInfo);
    }

    @Autowired
    private UserInfoService userInfoService;
    //回复加入团队后加入项目
    @GetMapping("replayToProject.json")
    public Boolean repalyToProject(ReplyDto replyDto,HttpServletRequest request){
        HttpSession session = request.getSession();
        UserInfoDto userInfo = (UserInfoDto) session.getAttribute("userInfo");
//        //创建人是userInfo
//        UserInfoDto userInfo = userInfoService.getByLoginName("281828254@qq.com");
        replyDto.setUserId(userInfo.getId());
        return projectService.replyToProject(replyDto,userInfo);
    }
}

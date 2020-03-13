package com.dapeng.springboot.service;

import com.dapeng.springboot.dto.InviteProjectDto;
import com.dapeng.springboot.dto.ProjectInfoDto;
import com.dapeng.springboot.dto.UserInfoDto;
import com.dapeng.springboot.entity.ProjectEntity;
import com.dapeng.springboot.entity.ProjectUserReltiveEntity;
import com.dapeng.springboot.entity.UserInfoEntity;
import com.dapeng.springboot.jpa.ProjectDao;
import com.dapeng.springboot.jpa.ProjectUserDao;
import com.dapeng.springboot.jpa.UserInfoDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

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
    @Resource
    private UserInfoDao userInfoDao;

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


    /**
     * 查询成员所在项目列表
     * @param userId
     * @return
     */
    public List<ProjectInfoDto> listProjectByUserId(Long userId) {
        List<ProjectEntity> entities = projectDao.queryByUserId(userId);
        if (entities == null || entities.size() < 1){
            return null;
        }
        List<ProjectInfoDto> infoDtos = new ArrayList<>();
        entities.forEach(projectEntity -> {
            ProjectInfoDto infoDto = new ProjectInfoDto();
            BeanUtils.copyProperties(projectEntity,infoDto);
            infoDtos.add(infoDto);
        });
        return infoDtos;
    }

    //查询项目下所有的成员
    public List<UserInfoDto> queryUsersByProject(Long projectId) {

        List<ProjectUserReltiveEntity> reltiveEntities = projectUserDao.findByProjectId(projectId);
        if(reltiveEntities == null || reltiveEntities.size()<1){
            return null;
        }
        List<UserInfoDto> userInfoDtos = new ArrayList<>();
        reltiveEntities.forEach(reltiveEntity -> {
            UserInfoEntity userInfoEntity = userInfoDao.getOne(reltiveEntity.getUserId());
            UserInfoDto userInfoDto = new UserInfoDto();
            userInfoDto.setId(userInfoEntity.getId());
            userInfoDto.setUserName(userInfoEntity.getUserName());
            userInfoDto.setLoginName(userInfoEntity.getLoginName());
            //查询角色信息
            userInfoDto.setProjectRole(reltiveEntity.getProjectRole());
            userInfoDtos.add(userInfoDto);
        });
        return userInfoDtos;
    }

    //根据项目id和用户id查询
    private ProjectUserReltiveEntity getByPidAndUid(Long projectId,Long userId){
        return projectUserDao.findByProjectIdAndUserId(projectId,userId);
    }

    //更新用户权限
    public Boolean updateUserRole(Long userId, Long projectId,String projectRole) {
        ProjectUserReltiveEntity reltiveEntity = projectUserDao.findByProjectIdAndUserId(projectId,userId);
        if (reltiveEntity == null){
            throw new RuntimeException("没有加入项目，无法设置权限");
        }
        if (!StringUtils.isEmpty(reltiveEntity.getProjectRole()) && reltiveEntity.getProjectRole().equals("超级管理员")){
            throw new RuntimeException("不能修改超级管理员权限");
        }
        int update = projectUserDao.updateRoleByPidAndUid(userId,projectId,projectRole);
        return update == 1;
    }

    /**
     * 更新项目
     * @param id
     * @param projectName
     * @param descript
     * @return
     */
    public Boolean updateProjectById(Long id, String projectName, String descript) {
        if (projectDao.findById(id) == null){
            throw new RuntimeException("项目不存在");
        }
        return projectDao.updateInfoById(id,projectName,descript) == 1;
    }

    /**
     * 邀请成员入项目
     * @param inviteProjectDto
     * @return
     */
    public Boolean inviteIntoProject(InviteProjectDto inviteProjectDto,UserInfoDto createInfo) {
        UserInfoEntity userInfo = userInfoDao.getOne(inviteProjectDto.getUserId());
        if (userInfo == null){
            throw new RuntimeException("邀请成员不存在");
        }
        Long teamId = createInfo.getTeamId();
        if (userInfo.getTeamId() != null && userInfo.getTeamId() == teamId){
            //团队成员，直接加入项目
            ProjectUserReltiveEntity reltiveEntity = new ProjectUserReltiveEntity();
            reltiveEntity.setProjectId(inviteProjectDto.getProjectId());
            reltiveEntity.setUserId(userInfo.getId());
            reltiveEntity.setUserName(userInfo.getUserName());
            reltiveEntity.setProjectRole("编辑者");
            projectUserDao.save(reltiveEntity);
            return true;
        }
        //如果是其他团队的成员
        if (userInfo.getTeamId() != null && userInfo.getTeamId() != teamId){
            throw new RuntimeException("已在其他团队中，无法加入当前项目");
        }
        //没有在团队中，邀请加入团队，生成消息

        return true;
    }
}

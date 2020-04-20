package com.zibing.springboot.service;

import com.zibing.springboot.common.ConstantRecord;
import com.zibing.springboot.dto.*;
import com.zibing.springboot.entity.ProjectEntity;
import com.zibing.springboot.entity.ProjectUserReltiveEntity;
import com.zibing.springboot.entity.TeamEntity;
import com.zibing.springboot.entity.UserInfoEntity;
import com.zibing.springboot.jpa.ProjectDao;
import com.zibing.springboot.jpa.ProjectUserDao;
import com.zibing.springboot.jpa.TeamDao;
import com.zibing.springboot.jpa.UserInfoDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zibing
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
    @Resource
    private NoticeService noticeService;
    @Autowired
    private TeamDao teamDao;

    /**
     * 创建项目
     *
     * @param projectInfoDto
     * @return
     */
    @Transactional
    public ProjectInfoDto createProject(ProjectInfoDto projectInfoDto) {
        ProjectEntity entity = new ProjectEntity();
        BeanUtils.copyProperties(projectInfoDto, entity);
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
     *
     * @param userId
     * @return
     */
    public List<ProjectInfoDto> listProjectByUserId(Long userId) {
        //查询用户所在项目
        List<ProjectUserReltiveEntity> reltiveEntities = projectUserDao.findByUserId(userId);
        if (reltiveEntities == null || reltiveEntities.size() < 1) {
            return new ArrayList<>();
        }
        List<ProjectInfoDto> infoDtos = new ArrayList<>();
        reltiveEntities.forEach(reltiveEntity -> {
            ProjectEntity entity = projectDao.getOne(reltiveEntity.getProjectId());
            ProjectInfoDto infoDto = new ProjectInfoDto();
            BeanUtils.copyProperties(entity, infoDto);
            infoDto.setCreateName(userInfoDao.getOne(entity.getCreateBy()).getUserName());
            infoDtos.add(infoDto);
        });
        return infoDtos;
    }

    //查询项目下所有的成员
    public List<UserInfoDto> queryUsersByProject(Long projectId) {

        List<ProjectUserReltiveEntity> reltiveEntities = projectUserDao.findByProjectId(projectId);
        if (reltiveEntities == null || reltiveEntities.size() < 1) {
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
            //邀请人
            if ("02".equals(userInfoEntity.getUserType())){
                userInfoDto.setInviteId(userInfoEntity.getId());
                userInfoDto.setInviteName(userInfoEntity.getUserName());
            } else {
                Long teamId = userInfoEntity.getTeamId();
                TeamEntity teamEntity = teamDao.getOne(teamId);
                userInfoDto.setInviteId(teamEntity.getCreateBy());
                userInfoDto.setInviteName(userInfoDao.getOne(teamEntity.getCreateBy()).getUserName());
            }
            userInfoDtos.add(userInfoDto);
        });
        return userInfoDtos;
    }

    //根据项目id和用户id查询
    private ProjectUserReltiveEntity getByPidAndUid(Long projectId, Long userId) {
        return projectUserDao.findByProjectIdAndUserId(projectId, userId);
    }

    //更新用户权限
    public Boolean updateUserRole(Long userId, Long projectId, String projectRole) {
        ProjectUserReltiveEntity reltiveEntity = projectUserDao.findByProjectIdAndUserId(projectId, userId);
        if (reltiveEntity == null) {
            throw new RuntimeException("没有加入项目，无法设置权限");
        }
        if (!StringUtils.isEmpty(reltiveEntity.getProjectRole()) && reltiveEntity.getProjectRole().equals("超级管理员")) {
            throw new RuntimeException("不能修改超级管理员权限");
        }
        int update = projectUserDao.updateRoleByPidAndUid(userId, projectId, projectRole);
        return update == 1;
    }

    /**
     * 更新项目
     *
     * @param id
     * @param projectName
     * @param descript
     * @return
     */
    public Boolean updateProjectById(Long id, String projectName, String descript) {
        if (projectDao.findById(id) == null) {
            throw new RuntimeException("项目不存在");
        }
        return projectDao.updateInfoById(id, projectName, descript) == 1;
    }

    /**
     * 邀请成员入项目
     *
     * @param inviteProjectDto
     * @return
     */
    @Transactional
    public Boolean inviteIntoProject(InviteProjectDto inviteProjectDto, UserInfoDto createInfo) {
        UserInfoEntity userInfo = userInfoDao.getOne(inviteProjectDto.getUserId());
        if (userInfo == null) {
            throw new RuntimeException("邀请成员不存在");
        }
        Long teamId = createInfo.getTeamId();
        if (userInfo.getTeamId() != null && userInfo.getTeamId() == teamId) {
            //团队成员，直接加入项目
            log.info("团队成员邀请入项目");
            ProjectUserReltiveEntity reltiveEntity = new ProjectUserReltiveEntity();
            reltiveEntity.setProjectId(inviteProjectDto.getProjectId());
            reltiveEntity.setUserId(userInfo.getId());
            reltiveEntity.setUserName(userInfo.getUserName());
            reltiveEntity.setProjectRole("编辑者");
            projectUserDao.save(reltiveEntity);
            return true;
        }
        //如果是其他团队的成员
        if (userInfo.getTeamId() != null && userInfo.getTeamId() != teamId) {
            throw new RuntimeException("已在其他团队中，无法加入当前项目");
        }
        //没有在团队中，邀请加入团队，生成消息
        log.info("非团队成员邀请入项目");
        ProjectEntity entity = getProjectByProjectId(inviteProjectDto.getProjectId());
        NoticeDto noticeDto = buildNoticeInfo(inviteProjectDto.getProjectId(), entity.getProjectName(),
                inviteProjectDto.getUserId(), userInfo.getUserName(), createInfo.getTeamId(), createInfo.getTeamName(),
                createInfo.getId(), createInfo.getUserName(), "invite", null);
        noticeService.createNotice(noticeDto);
        return true;
    }

    private ProjectEntity getProjectByProjectId(Long projectId) {
        return projectDao.getOne(projectId);
    }

    //构建通知消息
    private NoticeDto buildNoticeInfo(Long projectId, String projectName, Long handlerId, String handlerName, Long teamId, String teamName, Long createBy, String createName, String flag, String isOk) {
        NoticeDto noticeDto = new NoticeDto();
        noticeDto.setProjectId(projectId);
        noticeDto.setProjectName(projectName);
        noticeDto.setHandlerId(handlerId);//处理人
        noticeDto.setHandlerName(handlerName);
        noticeDto.setCreateBy(createBy);
        noticeDto.setCreateName(createName);
        noticeDto.setTeamId(teamId);
        noticeDto.setTeamName(teamName);
        StringBuffer sb = new StringBuffer();
        if ("invite".equals(flag)) {
            noticeDto.setAction(ConstantRecord.inviteAction);//邀请通知
            sb.append("用户");
            if (!StringUtils.isEmpty(createName)) {
                sb.append(createName);
            }
            sb.append("邀请您加入");
            sb.append(projectName).append("项目");
        } else {
            noticeDto.setAction(ConstantRecord.replyAction);//加入通知
            sb.append("用户");
            if (!StringUtils.isEmpty(createName)) {
                sb.append(createName);
            }
            if (!"ok".equals(isOk)) {
                sb.append("已拒绝加入");
            } else {
                sb.append("已加入");
            }
            sb.append(projectName).append("项目");
        }
        noticeDto.setContent(sb.toString());
        noticeDto.setStatus("未读");
        return noticeDto;
    }


    //回复入项目 先加入团队，再加入项目\
    @Transactional
    public Boolean replyToProject(ReplyDto replyDto, UserInfoDto userInfo) {
        //判断是否加入
        log.info("回复邀请项目通知入参，{}", replyDto);
        NoticeDto noticeDto = getByNoticeId(replyDto.getNoticeId());
        if (!"ok".equals(replyDto.getIsOk())) {
            //生成拒绝的数据
            //未加入团队通知给发起人
            NoticeDto replyNotice = buildNoticeInfo(noticeDto.getProjectId(), noticeDto.getProjectName(),
                    noticeDto.getCreateBy(), noticeDto.getCreateName(), noticeDto.getTeamId(), noticeDto.getTeamName(), replyDto.getUserId(), userInfo.getUserName(), "reply", "no");
            noticeService.createNotice(replyNotice);
            return false;
        }
        //同意加入
        if (userInfo.getTeamId() != null) {
            throw new RuntimeException("成员已在其他团队里");
        }
        //查询通知详情 加入团队
        userInfoDao.addTeamId(noticeDto.getTeamId(), noticeDto.getTeamName(), replyDto.getUserId());
        //加入项目
        ProjectUserReltiveEntity reltiveEntity = new ProjectUserReltiveEntity();
        reltiveEntity.setProjectId(noticeDto.getProjectId());
        reltiveEntity.setUserId(userInfo.getId());
        reltiveEntity.setUserName(userInfo.getUserName());
        reltiveEntity.setProjectRole("编辑者");
        log.info("entity={}", reltiveEntity);
        projectUserDao.save(reltiveEntity);
        //加入团队通知给发起人
        NoticeDto replyNotice = buildNoticeInfo(noticeDto.getProjectId(), noticeDto.getProjectName(),
                noticeDto.getCreateBy(), noticeDto.getCreateName(), noticeDto.getTeamId(), noticeDto.getTeamName(), replyDto.getUserId(), userInfo.getUserName(), "reply", "ok");
        noticeService.createNotice(replyNotice);
        log.info("回复邀请通知结束");
        return true;
    }

    private NoticeDto getByNoticeId(Long noticeId) {
        return noticeService.getById(noticeId);
    }

    //根据id查询
    public ProjectInfoDto findById(Long projectId) {
        ProjectEntity entity = projectDao.getOne(projectId);
        if (entity == null) {
            throw new RuntimeException("未查询到项目");
        }
        ProjectInfoDto projectInfoDto = new ProjectInfoDto();
        BeanUtils.copyProperties(entity, projectInfoDto);
        projectInfoDto.setCreateName(userInfoDao.getOne(entity.getCreateBy()).getUserName());
        return projectInfoDto;
    }
}

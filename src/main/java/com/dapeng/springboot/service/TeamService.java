package com.dapeng.springboot.service;

import com.dapeng.springboot.common.ConstantRecord;
import com.dapeng.springboot.dto.*;
import com.dapeng.springboot.entity.TeamEntity;
import com.dapeng.springboot.entity.UserInfoEntity;
import com.dapeng.springboot.jpa.TeamDao;
import com.dapeng.springboot.jpa.UserInfoDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

/**
 * @author lipeng
 * @version 1.0
 * @date 2020/3/10 13:47
 * @message：团队服务
 */
@Slf4j
@Service
public class TeamService {

    @Resource
    private TeamDao teamDao;
    @Resource
    private UserInfoDao userInfoDao;
    @Autowired
    private NoticeService noticeService;

    /**
     * 创建团队
     * @return
     */
    public Boolean createTeam(TeamDto teamDto){
        log.info("创建团队入参：{}",teamDto);
        if(getByTeamName(teamDto.getTeamName())!=null){
            throw new RuntimeException("团队名称已存在");
        }
        TeamEntity entity = new TeamEntity();
        BeanUtils.copyProperties(teamDto,entity);
        entity.setCreateBy(teamDto.getUserId());
        UserInfoEntity userInfo = userInfoDao.getOne(teamDto.getUserId());
        if (userInfo != null){
            entity.setCreateName(userInfo.getUserName());
        }
        TeamEntity resultEntity = teamDao.save(entity);
        userInfoDao.addTeamId(resultEntity.getId(),resultEntity.getTeamName(),teamDto.getUserId());
        return Boolean.TRUE;
    }

    private TeamEntity getByTeamName(String teamName) {

        return teamDao.findByTeamName(teamName);
    }

    /**
     * 更新团队名称
     * @param id
     * @param teamName
     * @return
     */
    public boolean updateTeam(Long id,String teamName){
        getById(id);
        int count = teamDao.updateTeamName(id,teamName);
        return count == 1;
    }

    private TeamEntity getById(Long id){
        TeamEntity entity = teamDao.getOne(id);
        if (entity == null){
            throw new RuntimeException("未查询到团队信息");
        }
        return entity;
    }

    /**
     * 邀请成员，生成消息
     * @param inviteDto
     * @return
     */
    public Boolean inviteUser(InviteTeamDto inviteDto) {
        UserInfoEntity userInfo = getUserInfoById(inviteDto.getUserId());
        if (userInfo.getTeamId() != null){
            throw new RuntimeException("用户已加入团队");
        }
        //生成通知信息
        //消息创建者就是团长
        UserInfoEntity owerInfo = getUserInfoById(inviteDto.getCreateBy());
        TeamEntity teamEntity = getById(owerInfo.getTeamId());
        NoticeDto noticeDto = buildNoticeInfo(inviteDto.getUserId(),userInfo.getUserName(),teamEntity.getId(),teamEntity.getTeamName(),inviteDto.getCreateBy(),"invite",null);
        noticeService.createNotice(noticeDto);
        return Boolean.TRUE;
    }

    //构建通知消息
    private NoticeDto buildNoticeInfo(Long handlerId,String handlerName,Long teamId,String teamName,Long createBy,String flag,String isOk) {
        NoticeDto noticeDto = new NoticeDto();
        noticeDto.setHandlerId(handlerId);//处理人
        noticeDto.setHandlerName(handlerName);
        UserInfoEntity createUser = getUserInfoById(createBy);
        log.info("结果：{}",createUser);
        noticeDto.setCreateBy(createBy);
        noticeDto.setCreateName(createUser.getUserName());
        noticeDto.setTeamId(teamId);
        noticeDto.setTeamName(teamName);
        StringBuffer sb = new StringBuffer();
        if ("invite".equals(flag)){
            noticeDto.setAction(ConstantRecord.inviteAction);//邀请通知
            sb.append("用户");
            if (!StringUtils.isEmpty(createUser.getUserName())){
                sb.append(createUser.getUserName());
            }
            sb.append("邀请您加入");
            sb.append(teamName).append("团队");
        } else {
            noticeDto.setAction(ConstantRecord.replyAction);//加入通知
            sb.append("用户");
            if (!StringUtils.isEmpty(createUser.getUserName())){
                sb.append(createUser.getUserName());
            }
            if (!"ok".equals(isOk)){
                sb.append("已拒绝加入");
            } else {
                sb.append("已加入");
            }
            sb.append(teamName).append("团队");
        }
        noticeDto.setContent(sb.toString());
        noticeDto.setStatus("未读");
        return noticeDto;
    }


    /**
     * 回复入团
     * @return
     */
    public boolean replyUser(ReplyDto replyDto){
        log.info("回复邀请通知入参，{}",replyDto);
        NoticeDto noticeDto = getByNoticeId(replyDto.getNoticeId());
        if (!"OK".equals(replyDto.getIsOk())){
            //生成拒绝的数据
            //加入团队通知给发起人
            NoticeDto replyNotice = buildNoticeInfo(noticeDto.getCreateBy(),noticeDto.getCreateName(), noticeDto.getTeamId(),noticeDto.getTeamName(),replyDto.getUserId(),"reply","ok");
            noticeService.createNotice(replyNotice);
            return false;
        }
        UserInfoEntity userInfo = getUserInfoById(replyDto.getUserId());
        if (userInfo.getTeamId() != null){
            throw new RuntimeException("成员已在其他团队里");
        }
        //查询通知详情
        userInfoDao.addTeamId(noticeDto.getTeamId(),noticeDto.getTeamName(),replyDto.getUserId());
        //加入团队通知给发起人
        NoticeDto replyNotice = buildNoticeInfo(noticeDto.getCreateBy(),noticeDto.getCreateName(), noticeDto.getTeamId(),noticeDto.getTeamName(),replyDto.getUserId(),"reply","ok");
        noticeService.createNotice(replyNotice);
        log.info("回复邀请通知结束");
        return Boolean.TRUE;
    }

    private NoticeDto getByNoticeId(Long noticeId){
       return noticeService.getById(noticeId);
    }

    //获取用户信息
    private UserInfoEntity getUserInfoById(Long userId){
        UserInfoEntity userInfo = userInfoDao.getOne(userId);
        if (userInfo == null){
            throw new RuntimeException("用户不存在");
        }
        return userInfo;
    }
}

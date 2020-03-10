package com.dapeng.springboot.service;

import com.dapeng.springboot.dto.TeamDto;
import com.dapeng.springboot.entity.TeamEntity;
import com.dapeng.springboot.entity.UserInfoEntity;
import com.dapeng.springboot.jpa.TeamDao;
import com.dapeng.springboot.jpa.UserInfoDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

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

    /**
     * 创建团队
     * @return
     */
    public Boolean createTeam(TeamDto teamDto){
        log.info("创建团队入参：{}",teamDto);
        TeamEntity entity = new TeamEntity();
        BeanUtils.copyProperties(teamDto,entity);
        entity.setCreateBy(teamDto.getUserId());
        UserInfoEntity userInfo = userInfoDao.getOne(teamDto.getUserId());
        if (userInfo != null){
            entity.setCreateName(userInfo.getUserName());
        }
        teamDao.save(entity);
        return Boolean.TRUE;
    }
}

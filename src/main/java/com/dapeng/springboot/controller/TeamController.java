package com.dapeng.springboot.controller;

import com.dapeng.springboot.dto.InviteTeamDto;
import com.dapeng.springboot.dto.ReplyDto;
import com.dapeng.springboot.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author lipeng
 * @version 1.0
 * @date 2020/3/10 17:38
 * @message：团队控制层
 */
@RestController
@RequestMapping("/team/")
public class TeamController {

    @Autowired
    private TeamService teamService;

    /**
     * 创建团队 注册团队负责人时一起创建
     *
     * @param
     * @return
     */
//    @PostMapping("createTeam.json")
//    public Boolean createTeam(@Valid @RequestBody TeamDto teamDto) {
//        return teamService.createTeam(teamDto);
//    }
    @PostMapping("updateTeamName.json")
    public boolean updateTeamName(@Param("id") Long id, @Param("teamName") String teamName) {
        return teamService.updateTeam(id, teamName);
    }

    //回复消息
    @PostMapping("replyNotice.json")
    public Boolean acceptUser(@Valid @RequestBody ReplyDto replyDto) {
        return teamService.replyUser(replyDto);
    }

    //邀请入团
    @PostMapping("invite.json")
    public Boolean inviteUser(@Valid @RequestBody InviteTeamDto inviteDto) {
        return teamService.inviteUser(inviteDto);
    }
}

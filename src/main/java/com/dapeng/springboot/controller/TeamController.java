package com.dapeng.springboot.controller;

import com.dapeng.springboot.dto.InviteDto;
import com.dapeng.springboot.dto.TeamDto;
import com.dapeng.springboot.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
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
     * 创建团队
     * @param teamDto
     * @return
     */
    @PostMapping("createTeam.json")
    public Boolean createTeam(@Valid @RequestBody TeamDto teamDto) {
        return teamService.createTeam(teamDto);
    }

    @PostMapping("invite.json")
    public Boolean inviteUser(@Valid @RequestBody InviteDto inviteDto){
        return teamService.invite(inviteDto);
    }
}

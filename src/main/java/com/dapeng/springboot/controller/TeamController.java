package com.dapeng.springboot.controller;

import cn.hutool.system.UserInfo;
import com.dapeng.springboot.dto.InviteTeamDto;
import com.dapeng.springboot.dto.ReplyDto;
import com.dapeng.springboot.dto.TeamDto;
import com.dapeng.springboot.dto.UserInfoDto;
import com.dapeng.springboot.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

/**
 * @author lipeng
 * @version 1.0
 * @date 2020/3/10 17:38
 * @message：团队控制层
 */
@RestController
@RequestMapping("api/team/")
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
    public boolean updateTeamName(@RequestBody TeamDto teamDto, HttpServletRequest request) {
        HttpSession session = request.getSession();
        return teamService.updateTeam(teamDto.getId(), teamDto.getTeamName(),session);
    }

    //回复消息
    @PostMapping("replyNotice.json")
    public Boolean acceptUser(@Valid @RequestBody ReplyDto replyDto,HttpServletRequest request) {
        HttpSession session = request.getSession();
        UserInfoDto userInfoDto = (UserInfoDto) session.getAttribute("userInfo");
        replyDto.setUserId(userInfoDto.getId());
        return teamService.replyUser(replyDto);
    }

    //邀请入团
    @PostMapping("invite.json")
    public Boolean inviteUser(@Valid @RequestBody InviteTeamDto inviteDto) {
        return teamService.inviteUser(inviteDto);
    }


    /**
     * 根据团队id查询下成员
     * @param id
     * @return
     */
    @GetMapping("listUser.json")
    public List<UserInfoDto> listUser(Long id){
        return teamService.listUserById(id);
    }
}

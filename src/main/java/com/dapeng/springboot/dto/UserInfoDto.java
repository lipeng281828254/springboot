package com.dapeng.springboot.dto;

import lombok.Data;
import org.springframework.util.StringUtils;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author lipeng
 * @version 1.0
 * @date 2020/3/8 0:04
 * @message：
 */
@Data
public class UserInfoDto implements Serializable {


    //用户id
    private Long id;

    //成员名称
    private String userName;

    //登录名 必须是邮箱
    @NotBlank(message = "邮箱不能为空")
    @Email(message = "邮箱格式非法")
    private String loginName;

    //密码
    @NotBlank(message = "密码不能为空")
    private String password;

    //确认密码
    @NotBlank(message = "确认密码不能为空")
    private String confirmPassword;

    //用户类型 传中文 01-成员，02-团队负责人
    @NotBlank(message = "注册裂隙不能为空，团长或成员")
    private String userType;

    //图片地址
    private String headImgId;

    //团队id
    private Long teamId;

    //团队名称
    private String teamName;

    /************项目信息*************/
    //所在项目中的角色
    private String projectRole;

    //项目邀请人userid
    private Long inviteId;

    //项目邀请人
    private String inviteName;



    public void checkTeamName() {
        if ("02".equals(userType) && StringUtils.isEmpty(teamName)) {
            throw new RuntimeException("团队负责人注册，团队名称不能为空");
        }
    }

}

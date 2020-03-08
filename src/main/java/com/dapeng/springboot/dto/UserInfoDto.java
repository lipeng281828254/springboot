package com.dapeng.springboot.dto;

import lombok.Data;

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

    //用户类型 传中文 成员，团长
    private String userType;

    //图片地址
    private String headImgId;




}

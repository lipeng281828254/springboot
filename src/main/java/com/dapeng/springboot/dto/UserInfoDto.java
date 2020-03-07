package com.dapeng.springboot.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author lipeng
 * @version 1.0
 * @date 2020/3/8 0:04
 * @message：
 */
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


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getHeadImgId() {
        return headImgId;
    }

    public void setHeadImgId(String headImgId) {
        this.headImgId = headImgId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "UserInfoDto{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", loginName='" + loginName + '\'' +
                ", password='" + password + '\'' +
                ", confirmPassword='" + confirmPassword + '\'' +
                ", userType='" + userType + '\'' +
                ", headImgId='" + headImgId + '\'' +
                '}';
    }


}

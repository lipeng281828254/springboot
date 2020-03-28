package com.dapeng.springboot.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author lipeng
 * @version 1.0
 * @date 2020/3/8 23:21
 * @message：
 */
@Data
public class UpLoginDto implements Serializable {


    private Long id;

    //登录名 必须是邮箱
    @NotBlank(message = "邮箱不能为空")
    @Email(message = "邮箱格式非法")
    private String loginName;

    //验证码
    private String valiCode;


}

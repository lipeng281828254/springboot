package com.dapeng.springboot.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author zibing
 * @version 1.0
 * @date 2020/3/9 23:37
 * @message：登录实体
 */
@Data
public class LoginDto implements Serializable {

    @NotBlank(message = "登录名不能为空")
    private String loginName;

    @NotBlank(message = "登录密码不能为空")
    private String password;
}

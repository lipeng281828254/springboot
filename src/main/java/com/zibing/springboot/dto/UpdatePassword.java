package com.zibing.springboot.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author zibing
 * @version 1.0
 * @date 2020/3/9 0:25
 * @message：
 */
@Data
public class UpdatePassword implements Serializable {

    private Long id;

    @NotBlank(message = "新密码不能为空")
    private String password;

    //确认密码
    @NotBlank(message = "确认密码不能为空")
    private String confirmPassword;

}
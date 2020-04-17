package com.zibing.springboot.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author zibing
 * @version 1.0
 * @date 2020/3/8 23:25
 * @message：
 */
@Data
public class ChekcPasswordDto implements Serializable {

    @NotNull(message = "用户id不能为空")
    private Long id;

    @NotBlank(message = "密码不能为空")
    private String password;
}

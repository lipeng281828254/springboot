package com.dapeng.springboot.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author zibing
 * @version 1.0
 * @date 2020/3/10 13:48
 * @message：
 */
@Data
public class TeamDto implements Serializable {

    private Long id;

    @NotBlank(message = "团队名称不能为空")
    private String teamName;

    @NotNull(message = "创建人不能为空")
    private Long userId;

}

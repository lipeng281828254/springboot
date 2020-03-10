package com.dapeng.springboot.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author lipeng
 * @version 1.0
 * @date 2020/3/10 18:44
 * @message：邀请入团实体
 */
@Data
public class InviteDto implements Serializable {

    /**
     * 邀请用户id
     */
    @NotBlank(message = "被邀请人不能为空")
    private Long userId;

    @NotNull(message = "团队不能为空")
    private Long teamId;

}

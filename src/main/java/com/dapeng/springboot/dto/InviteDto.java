package com.dapeng.springboot.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author lipeng
 * @version 1.0
 * @date 2020/3/10 18:44
 * @message：回复入团实体
 */
@Data
public class InviteDto implements Serializable {

    /**
     * 邀请用户id
     */
    @NotNull(message = "成员id不能为空")
    private Long userId;

    @NotNull(message = "团队创建人id不能为空")
    private Long createBy;

}

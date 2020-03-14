package com.dapeng.springboot.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author lipeng
 * @version 1.0
 * @date 2020/3/13 12:47
 * @message：邀请加入项目dto
 */
@Data
public class InviteProjectDto implements Serializable {


    //团队负责人id，项目创建者id
    private Long createBy;

    //邀请成员id
    @NotNull(message = "被邀请人id不能为空")
    private Long userId;

    //项目id
    @NotNull(message = "项目id不能为空")
    private Long projectId;
}

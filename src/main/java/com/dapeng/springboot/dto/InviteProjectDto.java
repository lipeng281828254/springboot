package com.dapeng.springboot.dto;

import lombok.Data;

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
    private Long userId;

    //项目id
    private Long projectId;
}

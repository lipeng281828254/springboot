package com.dapeng.springboot.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author lipeng
 * @version 1.0
 * @date 2020/3/10 18:44
 * @message：回复入团实体
 */
@Data
public class ReplyDto implements Serializable {

    /**
     * 当前用户id
     */
    private Long userId;

    /**
     * 通知id
     */
    private Long noticeId;

    /**
     * 是否加入 ok：同意加入；no:不同意
     */
    private String isOk;

}

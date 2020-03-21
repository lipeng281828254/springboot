package com.dapeng.springboot.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * @author lipeng
 * @version 1.0
 * @date 2020/3/10 22:51
 * @message：通知消息dto
 */
@Data
public class NoticeDto implements Serializable {


    private Long id;

    //项目id
    private Long projectId;

    private String projectName;

    //工作id
    private Long jobId;

    //10-迭代；20-需求；30-任务；40-缺陷
    private String jobType;

    //标题名称
    private String jobTitle;

    //事件类型：邀请通知；附件通知；属性变化通知
    private String action;

    //通知内容
    private String content;

    //处理人id
    private Long handlerId;

    //姓名
    private String handlerName;

    //创建人
    private Long createBy;

    //
    private String createName;

    //团队id
    private Long teamId;

    //团队名称
    private String teamName;

    //已读，未读
    private String status ="未读";

    //创建时间
    private Date createTime;
}

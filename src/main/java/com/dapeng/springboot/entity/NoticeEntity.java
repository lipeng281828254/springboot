package com.dapeng.springboot.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author lipeng
 * @version 1.0
 * @date 2020/3/10 22:51
 * @message：
 */
@Setter
@Getter
@Entity
@Table(name = "notice_info")
public class NoticeEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//自增设置
    @Column(name = "id",columnDefinition = "bigint(18) comment '主键'")
    private Long id;

    @Column(name = "project_id",columnDefinition = "bigint(18) comment '项目id'")
    private Long projectId;

    @Column(name = "project_name",columnDefinition = "varchar(256) comment '项目名称'")
    private String projectName;

    @Column(name = "job_id",columnDefinition = "bigint(18) comment '工作id'")
    private Long jobId;

    @Column(name = "job_type",columnDefinition = "varchar(32) comment '工作类型，任务，需求，迭代，缺陷'")
    private String jobType;

    @Column(name = "job_title",columnDefinition = "varchar(128) comment '需求名称，任务名称等'")
    private String jobTitle;

    @Column(name = "action",columnDefinition = "varchar(64) comment '通知事件  邀请通知，状态变化通知，评论通知等'")
    private String action;

    @Column(name = "content",columnDefinition = "varchar(256) comment '通知的内容'")
    private String content;

    @Column(name = "handler_id",columnDefinition = "bigint(18) comment '处理人，接收消息的人'")
    private Long handlerId;

    @Column(name = "handler_name",columnDefinition = "varchar(64) comment '处理人姓名'")
    private String handlerName;

    @Column(name = "create_by",columnDefinition = "bigint(18) comment '创建人'")
    private Long createBy;

    @Column(name = "create_name",columnDefinition = "varchar(64) comment '创建人姓名'")
    private String createName;

    @Column(name = "team_id",columnDefinition = "bigint(18) comment '团队id'")
    private Long teamId;

    @Column(name = "team_name",columnDefinition = "varchar(256) comment '团队名称'")
    private String teamName;

    @Column(name = "status",columnDefinition = "varchar(32) comment '消息状态，已读，未读'")
    private String status;

    @Column(
            name = "create_time",
            insertable = false,
            updatable = false,
            columnDefinition = " timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'"
    )
    private Date createTime;

    @Column(
            name = "update_time",
            insertable = false,
            updatable = false,
            columnDefinition = "timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间'"
    )
    private Date updateTime;

}

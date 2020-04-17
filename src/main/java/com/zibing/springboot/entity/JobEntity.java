package com.zibing.springboot.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author zibing
 * @version 1.0
 * @date 2020/3/16 12:57
 * @message：工作管理实体
 */
@Data
@Entity
@Table(name = "job_info")
public class JobEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//自增设置
    @Column(name = "id",columnDefinition = "bigint(18) comment '主键'")
    private Long id ;

    @Column(name = "title",columnDefinition = "varchar(128) comment '标题'")
    private String title;

    @Column(name = "status",columnDefinition = "varchar(32) comment '状态'")
    private String status;

    /**
     * 类型
     */
    @Column(name = "type",columnDefinition = "varchar(32) comment '类型'")
    private String type;

    @Column(name = "need_type",columnDefinition = "varchar(64) comment '需求类型'")
    private String needType;

    @Column(name = "need_from",columnDefinition = "varchar(64) comment '需求来源'")
    private String needFrom;

    @Column(name = "demand_id",columnDefinition = "bigint(18) comment '需求id'")
    private Long demandId;

    @Column(name = "iteration_id",columnDefinition = "bigint(18) comment '迭代id'")
    private Long iterationId;

    @Column(name = "iteration_name",columnDefinition = "varchar(256) comment '迭代名称'")
    private String iterationName;

    @Column(name = "project_id",columnDefinition = "bigint(18) comment '项目id'")
    private Long projectId;

    @Column(name = "project_name",columnDefinition = "varchar(256) comment '项目名称'")
    private String projectName;

    @Column(name = "priority_level",columnDefinition = "int(4) comment '优先级别'")
    private Integer priorityLevel;

    @Column(name = "create_by",columnDefinition = "bigint(18) comment '创建人id'")
    private Long createBy;

    @Column(name = "create_name",columnDefinition = "varchar(64) comment '创建人'")
    private String createName;

    @Column(name = "handler_id",columnDefinition = "bigint(18) comment '处理人id'")
    private Long handlerId;

    @Column(name = "handler_name",columnDefinition = "varchar(64) comment '处理人姓名'")
    private String handlerName;

    @Column(name = "start_date",columnDefinition = "varchar(64) comment '开始时间'")
    private String startDate;

    @Column(name = "end_date",columnDefinition = "varchar(64) comment '结束时间'")
    private String endDate;

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

    //描述
    @Column(name = "descript",columnDefinition = "varchar(1000) comment '描述'")
    private String descript;
    //附件路径
    @Column(name = "file_id",columnDefinition = "varchar(256) comment '附件路径'")
    private String fileId;
    //文件名称
    @Column(name = "file_name",columnDefinition = "varchar(256) comment '文件名称'")
    private String fileName;
}

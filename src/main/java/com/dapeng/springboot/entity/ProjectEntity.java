package com.dapeng.springboot.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author lipeng
 * @version 1.0
 * @date 2020/3/11 22:41
 * @message：项目entity
 */
@Setter
@Getter
@Entity
@Table(name = "project_info")
public class ProjectEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//自增设置
    @Column(name = "id",columnDefinition = "bigint(18) comment '主键'")
    private Long id;

    @Column(name = "project_name",columnDefinition = "varchar(256) comment '项目名称'")
    private String projectName;

    @Column(name = "descript",columnDefinition = "varchar(500) comment '项目描述'")
    private String descript;

    @Column(name = "status",columnDefinition = "varchar(32) comment '项目状态'")
    private String status;

    @Column(name = "create_by",columnDefinition = "bigint(18) comment '创建人'")
    private Long createBy;

    @Column(name = "create_name",columnDefinition = "varchar(64) comment '创建人姓名'")
    private String createName;

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

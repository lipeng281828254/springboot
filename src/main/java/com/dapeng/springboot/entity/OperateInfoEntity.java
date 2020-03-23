package com.dapeng.springboot.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author lipeng
 * @version 1.0
 * @date 2020/3/21 1:01
 * @message：操作纪律
 */
@Data
@Entity
@Table(name = "operate_log")
public class OperateInfoEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//自增设置
    @Column(name = "id", columnDefinition = "bigint(18) comment '主键'")
    private Long id;

    @Column(name = "job_id", columnDefinition = "bigint(18) comment '主体id'")
    private Long jobId;

    @Column(name = "operator_id", columnDefinition = "bigint(18) comment '操作人id'")
    private Long operatorId;

    @Column(name = "operator_name", columnDefinition = "varchar(64) comment '操作人姓名'")
    private String operatorName;

    @Column(name = "change_field", columnDefinition = "varchar(32) comment '变更字段'")
    private String changeField;

    @Column(name = "change_content_before", columnDefinition = "varchar(500) comment '变更前内容'")
    private String changeContentBefore;

    @Column(name = "change_content_after", columnDefinition = "varchar(500) comment '变更后内容'")
    private String changeContentAfter;

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

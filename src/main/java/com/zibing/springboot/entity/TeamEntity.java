package com.zibing.springboot.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author zibing
 * @version 1.0
 * @date 2020/3/10 17:09
 * @message：
 */
@Setter
@Getter
@Entity
@Table(name = "team_info")
public class TeamEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//自增设置
    @Column(name = "id",columnDefinition = "bigint(18) comment '主键'")
    private Long id;

    @Column(name = "team_name",columnDefinition = "varchar(256) comment '团队名'")
    private String teamName;

    @Column(name = "create_by",columnDefinition = "bigint(18) comment '创建人'")
    private Long createBy;

    @Column(name = "create_name",columnDefinition = "varchar(64) comment '创建人名称'")
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

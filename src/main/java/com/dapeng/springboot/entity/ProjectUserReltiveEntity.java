package com.dapeng.springboot.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author zibing
 * @version 1.0
 * @date 2020/3/11 23:10
 * @message：
 */
@ToString
@Setter
@Getter
@Entity
@Table(name = "project_user_relative")
public class ProjectUserReltiveEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//自增设置
    @Column(name = "id",columnDefinition = "bigint(18) comment '主键'")
    private Long id;

    //项目id
    @Column(name = "project_id",columnDefinition = "bigint(18) comment '项目id'")
    private Long projectId;

    //用户id
    @Column(name = "user_id",columnDefinition = "bigint(18) comment '用户id'")
    private Long userId;

    //用户名
    @Column(name = "user_name",columnDefinition = "varchar(64) comment '用户姓名'")
    private String userName;

    //项目中角色
    @Column(name = "project_role",columnDefinition = "varchar(32) comment '项目角色'")
    private String projectRole;


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

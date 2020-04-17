package com.zibing.springboot.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author zibing
 * @version 1.0
 * @date 2020/3/7 23:32
 * @message：用户信息entity
 */
@ToString
@Setter
@Getter
@Entity
@Table(name = "user_info")
public class UserInfoEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//自增设置
    @Column(name = "id",columnDefinition = "bigint(18) comment '主键'")
    private Long id;

    @Column(name = "user_name",columnDefinition = "varchar(64) comment '姓名'")
    private String userName;

    @Column(name = "login_name",columnDefinition = "varchar(64) comment '登录名'")
    private String loginName;

    @Column(name = "password",columnDefinition = "varchar(256) comment '密码'")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "head_img_id")
    private String headImgId;

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

    //成员，团长
    @Column(name = "user_type")
    private String userType;

    @Column(name = "team_id")
    private Long teamId;

    @Column(name = "team_name",columnDefinition = "varchar(256) comment '团队名称'")
    private String teamName;

    @Column(name = "position")
    private String position;


}

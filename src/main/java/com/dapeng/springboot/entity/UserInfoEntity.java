package com.dapeng.springboot.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author lipeng
 * @version 1.0
 * @date 2020/3/7 23:32
 * @message：用户信息entity
 */
@Data
@Entity
@Table(name = "user_info")
public class UserInfoEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//自增设置
    @Column(name = "id")
    private Long id;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "login_name")
    private String loginName;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "head_img_id")
    private String headImgId;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

    //成员，团长
    @Column(name = "user_type")
    private String userType;

    @Column(name = "term_id")
    private Long termId;

    @Column(name = "position")
    private String position;


}

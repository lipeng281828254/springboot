package com.zibing.springboot.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author zibing
 * @version 1.0
 * @date 2020/3/17 13:36
 * @message：
 */
@Data
@Entity
@Table(name = "comment_info")
public class CommentEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//自增设置
    @Column(name = "id", columnDefinition = "bigint(18) comment '主键'")
    private Long id;

    @Column(name = "biz_id", columnDefinition = "bigint(18) comment 'jobid'")
    private Long jobId;

    @Column(name = "comment_id", columnDefinition = "bigint(18) comment '评论人id'")
    private Long commentId;

    @Column(name = "comment_name", columnDefinition = "varchar(64) comment '评论人姓名'")
    private String commentName;

    @Column(name = "comment_content", columnDefinition = "varchar(10000) comment '评论内容'")
    private String commentContent;


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

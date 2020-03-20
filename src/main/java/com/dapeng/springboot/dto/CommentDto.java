package com.dapeng.springboot.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * @author lipeng
 * @version 1.0
 * @date 2020/3/17 14:10
 * @message： 评论信息实体
 */
@Data
public class CommentDto implements Serializable {


    private Long id;

    //jobid
    @NotNull(message = "评论工作id不能为空")
    private Long jobId;

    //评论人
    private Long commentId;

    //姓名
    private String commentName;

    //评论内容
    @NotBlank(message = "评论内容不能为空")
    private String commentContent;


    private Date createTime;

    private Date updateTime;
}

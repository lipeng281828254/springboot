package com.dapeng.springboot.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

/**
 * @author lipeng
 * @version 1.0
 * @date 2020/3/11 22:47
 * @message：项目信息实体
 */
@Data
public class ProjectInfoDto implements Serializable {

    private Long id;

    //项目名称
    @NotBlank(message = "项目名称不能为空")
    private String projectName;

    //项目描述
    private String descript;

    //项目状态 未完成，已完成
    private String status;

    private Long createBy;

    private String createName;

    private Date createTime;

    private Date updateTime;
}

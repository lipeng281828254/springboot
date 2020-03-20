package com.dapeng.springboot.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * @author lipeng
 * @version 1.0
 * @date 2020/3/21 1:07
 * @message：操作信息
 */
@Data
public class OperateInfoDto implements Serializable {

    private Long id;

    //变更需求，任务，迭代的id
    @NotNull(message = "变更主体id不能为空")
    private Long jobId;
    //变更人id
    private Long operatorId;
    //变更人姓名
    private String operatorName;
    //变更字段
    @NotBlank(message = "变更字段不能为空")
    private String changeField;
    //变更前内容
    @NotBlank(message = "变更前内容不能为空")
    private String changeContentBefore;
    //变更后内容
    @NotBlank(message = "变更后内容不能为空")
    private String changeContentAfter;
    private Date createTime;
    private Date updateTime;
}

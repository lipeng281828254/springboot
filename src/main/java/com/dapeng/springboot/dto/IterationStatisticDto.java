package com.dapeng.springboot.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class IterationStatisticDto implements Serializable {

    //需求数
    private Integer demandCount;

    //任务数
    private Integer taskCount;

    //缺陷数量
    private Integer defectCount;

    //迭代下总数
    private Integer totalCount;
}
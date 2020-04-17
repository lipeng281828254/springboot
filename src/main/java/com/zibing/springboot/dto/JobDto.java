package com.zibing.springboot.dto;

import lombok.Data;
import org.springframework.util.StringUtils;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * @author zibing
 * @version 1.0
 * @date 2020/3/16 13:16
 * @message：
 */
@Data
public class JobDto implements Serializable {

    //id
    private Long id ;

    //标题
    @NotBlank(message = "标题不能为空")
    private String title;

    //状态
    /**
     * 需求：待规划，规划中，实现中，已发布，已拒绝
     * 任务：测试提交，接受/处理，已解决，已验证，验证不通过，已拒绝，已挂起，已关闭
     * 缺陷：未开始，进行中，已完成
     */
    private String status;

    /**
     * 类型：需求，任务，缺陷，迭代
     */
    @NotBlank(message = "创建类型不能为空")
    private String type;

    /**
     * 需求分类:
     *    优化需求;新需求;技术需求
     */
    private String needType;

    /**
     * 需求来源 手书
     * 客户，用户，产品经理，客服，运营，技术支持，合作伙伴
     */
    private String needFrom;

    /**
     * 需求id
     */
    private Long demandId;

    /**
     * 迭代id
     */
    private Long iterationId;

    /**
     * 迭代名称
     */
    private String iterationName;

    /**
     * 项目id
     */
    @NotNull(message = "项目id不能为空")
    private Long projectId;
    /**
     * 项目名称
     */
    private String projectName;

    /**
     * 优先级：1,最高；2,高；3,普通；4,较低；5,低
     */
    private Integer priorityLevel;

    //创建人
    private Long createBy;

    //姓名
    private String createName;

    //处理人
    private Long handlerId;

    //处理人姓名
    private String handlerName;

    //迭代开始时间
    private String startDate;
    //迭代结束时间
    private String endDate;


    private Date createTime;


    private Date updateTime;

    //描述
    private String descript;

    //附件路径
    private String fileId;
    //文件名称
    private String fileName;

    public void check(){
        if (!StringUtils.isEmpty(fileId) && StringUtils.isEmpty(fileName)){
            throw new RuntimeException("文件名称不能为空");
        }
    }

    public void checkDd(){
        if (!"迭代".equals(type) && StringUtils.isEmpty(handlerId)){
            throw new RuntimeException("需求，任务，缺陷处理人必传");
        }
    }
}

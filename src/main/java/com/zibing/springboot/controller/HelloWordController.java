package com.zibing.springboot.controller;

import com.zibing.springboot.service.JobSerivce;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ==================================================
 * <p>
 * FileName: HelloWordController
 *
 * @author : zibing
 * @create 2019/9/23
 * @since 1.0.0
 * 〈功能〉：helloword的访问控制器
 * ==================================================
 */
@Slf4j
//@Controller
@RestController
public class HelloWordController {

    @Autowired
    private JobSerivce jobSerivce;

    @RequestMapping(value = "/")
    public String toIndex() {
        System.out.println("->>进入控制器");
        return "success";
//        return "index";
    }

}

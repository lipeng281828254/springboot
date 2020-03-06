package com.dapeng.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * ==================================================
 * <p>
 * FileName: HelloWordController
 *
 * @author : lipeng
 * @create 2019/9/23
 * @since 1.0.0
 * 〈功能〉：helloword的访问控制器
 * ==================================================
 */
@Controller
@RequestMapping("/test/")
public class HelloWordController {

    @RequestMapping(value = "/index")
    public String toIndex(){
        System.out.println("->>进入控制器");
        return "index";
    }
}

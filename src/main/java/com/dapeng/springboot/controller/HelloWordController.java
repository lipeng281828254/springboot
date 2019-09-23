package com.dapeng.springboot.controller;

import jdk.nashorn.internal.runtime.logging.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
@RestController
public class HelloWordController {

    @RequestMapping(value = "index",method = RequestMethod.GET)
    public String index(){
        System.out.println("->>进入控制器");
        return "HelloWord";
    }
}

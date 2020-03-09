package com.dapeng.springboot.util;

import org.springframework.util.Assert;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author lipeng
 * @version 1.0
 * @date 2020/3/10 0:21
 * @message：
 */
public class SessionUtil {

    public static HttpServletRequest getCurrnetSession(){
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        Assert.notNull(sra, "上下文中无ServletRequestAttributes");
        return sra.getRequest();
    }

    public static ServletRequestAttributes getRequestAttributes() {

        return (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    }

    public static HttpServletRequest getRequest() {
        return getRequestAttributes().getRequest();
    }


    public static void main(String[] args) {
        ServletRequestAttributes attributes = getRequestAttributes();
        System.out.println("attributes="+attributes);

        HttpServletRequest request = getRequest();
        System.out.println("值："+request);

        request.getSession().setAttribute("11","qq");

        System.out.println("值1："+request);
        String aa = (String) request.getSession().getAttribute("11");
        System.out.println("aa="+aa);
    }


}

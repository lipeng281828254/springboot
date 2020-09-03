package com.dapeng;

import java.util.Random;

/**
 * 获取随机字符串
 */
public class GetRondomStr {

    public static void main(String[] args) {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 18; i++) {
            int index = random.nextInt(base.length());
            sb.append(base.charAt(index));
        }
        System.out.println(sb.toString());
    }
}

package com.dapeng.util;

/**
 * 单例模式
 */
public class MySingleton {

    /**
     * 饿汉模式
     */
    private static MySingleton object = new MySingleton();

    //私有化构造函数
    private MySingleton() {

    }

    public static MySingleton create() {
        return object;
    }


}

package com.dapeng.util;

/**
 * 懒汉模式
 */
public class MySingleton2 {

    private static MySingleton2 mySingleton2 = null;

    private MySingleton2(){}

    public static MySingleton2 getInstance(){
        if (mySingleton2 == null){
            mySingleton2 = new MySingleton2();
        }
        return mySingleton2;
    }
}

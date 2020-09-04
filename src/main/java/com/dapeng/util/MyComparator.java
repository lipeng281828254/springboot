package com.dapeng.util;

import java.util.Comparator;

public class MyComparator implements Comparator {

    /**
     * 比较入参o1,o2 大于0 o1>o2
     * @param o1
     * @param o2
     * @return
     */
    @Override
    public int compare(Object o1, Object o2) {
        return o1.hashCode() - o2.hashCode();
    }

    public static void main(String[] args) {
        MyComparator myComparator = new MyComparator();
        Integer a = 2;
        Integer b = 4;
        System.out.println( myComparator.compare(b,a));
    }
}

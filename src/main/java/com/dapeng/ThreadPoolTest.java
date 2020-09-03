package com.dapeng;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ThreadPoolTest {


    public static void main(String[] args) {


        ExecutorService service = Executors.newFixedThreadPool(5);
        for (int i = 0;i<5;i++){
            Future future = service.submit(()->{
                System.out.println("start="+Thread.currentThread().getName());
                Object o = null;
                System.out.println("result="+o.toString());
            });
            try {
                future.get();
            } catch (Exception e){
                System.out.println(e);
            }

        }
    }

}

package com.gate.thread;

import java.util.ArrayList;
import java.util.List;

/**
 * volatile不能保证线程的原子性，所以无法替代synchronized.
 * 如下面程序，如果不加synchronized，最后输出的t1.count不一定等于100000，而且每次执行得到的值不一样；
 * 如果加上synchronized，得到的值就是100000。
 */
public class T09_Volatile02 {

    int count = 0;

    synchronized void m() {
        for (int i = 0; i < 100000; i++) {
            count++;
        }
    }

    public static void main(String[] args) {
        long startTime=System.currentTimeMillis();
        T09_Volatile02 t1 = new T09_Volatile02();
        List<Thread> threadList = new ArrayList<>();

        // 创建10个线程
        for (int i = 0; i < 10; i++) {
            threadList.add(new Thread(t1::m, "thread" + i));
        }
        // 启动10个线程
        threadList.forEach((o) -> o.start());

        threadList.forEach((o) -> {
            try {
                o.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        System.out.println(t1.count);
        long endTime=System.currentTimeMillis();
        long a = endTime - startTime;
        System.out.println("当前程序耗时："+ a +"ms");
    }
}

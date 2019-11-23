package com.gate.thread;

import java.util.concurrent.TimeUnit;

/**
 * 模拟银行账户。在写入的时候加锁，在读数据的时候不加锁。
 */
public class T06_SynchronizedDemo {

    String name;
    double blance;

    public /*synchronized*/ void addAccount(String name, double blance) {
        this.name = name;
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.blance = blance;
    }

    public synchronized double getAccount(String name) {
        return this.blance;
    }

    public static void main(String[] args) {
        T06_SynchronizedDemo demo = new T06_SynchronizedDemo();
        new Thread(() -> {
            demo.addAccount("zhangsan", 100);
        }).start();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(demo.getAccount("zhangsan") + " 1");

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(demo.getAccount("zhangsan") + " 2");

    }
}

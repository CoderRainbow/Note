package com.gate.thread;

import java.util.concurrent.TimeUnit;

/**
 * synchronized是可重入的。
 * 就是在一个synchronized方法内可以调用另一个synchronized方法。
 * synchronized必须的可重入的，不然会造成死锁。
 */
public class T07_Synchronized02 {

    synchronized void m1() {
        System.out.println("m1 start");
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        m2();
        System.out.println("m1 end");
    }

    synchronized void m2() {
        System.out.println("m2 start");
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("m2 end");
    }

    public static void main(String[] args) {
        T07_Synchronized02 synchronized02 = new T07_Synchronized02();
        synchronized02.m1();
    }
}

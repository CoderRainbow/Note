package com.gate.thread;

import java.util.concurrent.TimeUnit;

/**
 * 1.什么是进程：比如一个QQ.exe,一个程序运行起来叫做进程。
 * 2.什么是线程：线程是进程里的一个最小的执行单元。
 *              线程可以说是一个程序里不同的执行路径，如下面方法里的T01().start();
 */
public class T01_Thread {

    public static void main(String[] args) {
        /**
         * 调用线程的两种方式：
         * 1.new T01().run(); 方法调用
         * 2.new T01().start();  当调用start方法的时候，会产生一个分支，这个分支会和主方法的程序一起运行。
         */
        new T01().run();   // 调用此方法会先执行T01()里run的方法，再执行main里的方法。
        //new T01().start(); // 调用此方法会交替执行T01()里run的方法和main里的方法。
        for (int i = 0; i < 10; i++) {
            try {
                TimeUnit.MILLISECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("main");
        }
    }

    private static class T01 extends Thread {

        @Override
        public void run(){
            for (int i = 0; i < 10; i++) {
                try {
                    TimeUnit.MILLISECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("T0");
            }
        }
    }

}

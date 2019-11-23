package com.gate.thread;

/**
 * 创建线程的方法：
 * 1.继承Thread类。
 * 2.实现Runable接口。
 */

public class T02_CreateThread {

    // 1.继承Thread类
    static class Thread01 extends Thread {

        @Override
        public void run(){
            System.out.println("T02_Thread01");
        };
    }

    // 2.实现Runable接口
    static class Thread02 implements Runnable {

        @Override
        public void run(){
            System.out.println("T02_Thread02");
        }
    }

    public static void main(String[] args) {
        // 继承Thread类start线程的方式。
        new Thread01().start();
        // 实现Runable接口start线程的方式。
        new Thread(new Thread02()).start();
    }

}

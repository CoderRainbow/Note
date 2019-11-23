package com.gate.juc;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * CyclicBarrier:循环栅栏。栅栏前人满了，放开栅栏。人走后关上栅栏，人满再开。
 */

public class T06_CyclicBarrier {

    public static void main(String[] args) {
        // 只传一个参数的时候，没有第二个参数表示满20后什么也不做。
        // CyclicBarrier barrier = new CyclicBarrier(20);

        // Lambda表达式写法
        // CyclicBarrier barrier = new CyclicBarrier(20, ()-> System.out.println("Lambda 人满，发车"));

        // 第一个参数是满20发车，满20个参数后调用第二个参数。
        CyclicBarrier barrier = new CyclicBarrier(20, new Runnable() {
            @Override
            public void run() {
                System.out.println("人满，发车");
            }
        });
        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                try {
                    barrier.await();   // 什么时候够20个了（上面new的时候传入的参数），就执行下面的代码。
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}

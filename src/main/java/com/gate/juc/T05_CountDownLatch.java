package com.gate.juc;

import java.util.concurrent.CountDownLatch;

/**
 * CountDownLatch:倒数的门栓，5.4.3.2.1数到了门栓就开了。
 * latch.await()：门栓拴住门，不要动。当latch.await()变成1时，门自动打开。
 * 常用来等待多少线程结束之后在干什么。
 */
public class T05_CountDownLatch {

    private void usingCountDownLatch() {
        Thread[] threads = new Thread[100];
        CountDownLatch latch = new CountDownLatch(threads.length);
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(() -> {
                int result = 0;
                for (int j = 0; j < 10000; j++) {
                    result += j;
                    latch.countDown();
                }
            });
            System.out.println(i);
        }

        for (int i = 0; i < threads.length; i++) {
            threads[i].start();
        }

        try {
            latch.await();  // 当latch.await() 等于0的时候，继续执行下面的代码。
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("latch end");
    }

    public static void main(String[] args) {

        T05_CountDownLatch t = new T05_CountDownLatch();
        t.usingCountDownLatch();
    }

}

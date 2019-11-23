package com.gate.juc;

import java.util.concurrent.Semaphore;

/**
 * Semaphore
 * 功能：限流：最多允许多少个线程同时运行。
 */

public class T09_Semaphore {

    public static void main(String[] args) {
        // 参数1是允许的信号灯数量。
        // 参数1就是允许同时运行的线程数量。
        // Semaphore semaphore = new Semaphore(2);
        // 参数2 true代表公平锁。不写默认是非公平锁。
        Semaphore semaphore = new Semaphore(2,true);
        new Thread(() -> {
            try {
                // 阻塞方法，如果acquire不到的话就停在这里
                // acquire拿到new Semaphore的参数减去1.
                // acquire获得锁。
                semaphore.acquire();
                System.out.println("T1 running...");
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                // release将上面acquire减去1的值加上1.
                semaphore.release();
                System.out.println("T1 end...");
            }
        }).start();
        new Thread(() -> {
            try {
                // 阻塞方法，如果acquire不到的话就停在这里
                // acquire拿到new Semaphore的参数减去1.
                semaphore.acquire();
                System.out.println("T2 running...");
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                // release将上面acquire减去1的值加上1.
                semaphore.release();
                System.out.println("T2 end...");
            }
        }).start();
        new Thread(() -> {
            try {
                // 阻塞方法，如果acquire不到的话就停在这里
                // acquire拿到new Semaphore的参数减去1.
                semaphore.acquire();
                System.out.println("T3 running...");
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                // release将上面acquire减去1的值加上1.
                semaphore.release();
                System.out.println("T3 end...");
            }
        }).start();
    }
}

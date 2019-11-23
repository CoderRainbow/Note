package com.gate.thread;

/**
 * 线程的方法
 * 1.sleep:睡眠该线程，让给别的线程去运行。到了睡眠结束的时间自动复活。
 * 2.yield:退出该线程，进入等待队列里，返回到就绪状态。本质是让出一下CPU。
 * 3.join:如果在t1线程调用了t2.join()，那就是把t1加入到t2去运行，t1暂时等待t2运行完继续运行。经常用来等待另一个线程的结束。
 *        join()可以实现多个线程按顺序执行完。
 */
public class T03_Sleep_Yield_Join {

    public static void main(String[] args) {

        testJoin();
//        testSleep();
//        testYield();
    }

    // sleep
    static void testSleep() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    System.out.println("A" + i);
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    // yield
    static void testYield() {
        // Lambda 表达式创建线程
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                System.out.println("B" + i);
                if (i % 2 == 0) Thread.yield();
            }
        }).start();
    }

    // join
    static void testJoin() {
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                System.out.println("C" + i);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    System.out.println("D" + i);
                    t1.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        t1.start();
        t2.start();
    }
}

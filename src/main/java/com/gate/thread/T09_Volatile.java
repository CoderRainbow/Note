package com.gate.thread;

import java.util.concurrent.TimeUnit;

/**
 * volatile:
 * 1.保证线程可见性：
 *   比如下面的程序，不加volatile的时候，main()和T共同访问running，当main()从堆内存中把running拿到自己的线程内存中改变值之后，T线程不知道什么时候
 *   要去堆里面拿新的值，这就是线程不可见的意思，T线程不知道main()线程把running值改成了什么。
 *   加上volatile之后，main()改变了running的值，T线程就会拿到running被main()改变后的最新的值，这就是线程可见性。
 *   MESI：本质上使用了CPU的缓存一致性协议，多个CPU之间也需要进行缓存，由于不同的线程运行在不同的CPU上，所以A CPU改了B CPU不一定知道。但是CPU有缓存
 *   一致性协议，所以volatile也是通过CPU的缓存一致性实现的。
 * 2.禁止指令重排序：
 *
 * volatile并不能保证原子性，也就是无法保证多个线程同时修改running变量是所带来的不一致问题，所以volatile不能替代synchronized。T09_Volatile02.java
 */
public class T09_Volatile {

    // 对比下加了volatile和不加volatile的区别
    // 加了 volatile, m()方法会输出"m end"，因为加了volatile, running的值就一直是true，不会被main方法里改变。
    // 不加 volatile m()方法不会执行到"m end"，不加volatile，running的值就会被main方法里改成false。
    volatile boolean running = true;

    void m() {
        System.out.println("m start");
        while(running) {

        }
        System.out.println("m end");
    }

    public static void main(String[] args) {
        T09_Volatile t09Volatile = new T09_Volatile();
        // Lambda表达式，相当于 new Thread(new Runable(run(){m()}));
        new Thread(t09Volatile::m, "T").start();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t09Volatile.running = false;

    }

}

package com.gate.thread;

import java.util.concurrent.TimeUnit;

/**
 * 锁优化 synchronized优化。
 * 在锁的争用不是很剧烈的前提之下，锁的粒度最好小一些，也就是同步代码块中的内容尽量少些。
 * 锁的细化：当代码块中有业务代码，而业务代码不需要加锁，只有其中一小部分代码需要加锁时，锁应该加在那一小部分代码上，而不是加在整个方法上。
 * 锁的粗化：当业务代码中有特别多细小的锁的时候，锁的争用会很频繁，可以把这些细小的锁编程大锁加在方法上。
 */
public class T13_SynchronizedOptimization {

    int count = 0;

    synchronized void m1() {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        count++;
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    void m2() {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 只有这一部分代码需要锁，那就加在这一部分代码上。而不是像m1()一样加在整个方法上。
        // 采用细粒度锁，可以使线程争用时间变短，从而使效率提高。
        synchronized (this) {
            count++;
        }
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

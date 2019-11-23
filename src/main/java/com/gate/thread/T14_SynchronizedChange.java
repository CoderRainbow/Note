package com.gate.thread;

import java.util.concurrent.TimeUnit;

/**
 * 锁定对象o，如果o的属性发生变化，不影响锁的使用。
 * 但是如果o编程另一个对象，则锁定的对象会发生改变。这个时候线程的并发就会出问题。
 * 应该避免将锁定对象的引用变成另一个对象。
 * 如果要避免这个问题，在对象上使用final使其不可变。
 */
public class T14_SynchronizedChange {

    /*final*/ Object o = new Object();

    void m() {
        synchronized(o) {
            while(true) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName());
            }
        }
    }

    public static void main(String[] args) {
        T14_SynchronizedChange t = new T14_SynchronizedChange();
        new Thread(t::m, "t1").start();
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Thread t2 = new Thread(t::m, "t2");
        t.o = new Object(); //锁的对象发生改变，所以t2线程得以执行，如果注释掉这句话，线程t2将永远得不到执行。
        t2.start();
    }
}

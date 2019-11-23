package com.gate.juc;

import java.util.concurrent.TimeUnit;
/**
 * ReentrantLock vs synchronized
 * 1.ReentrantLock可以替代synchronized；
 * 2.ReentrantLock底层是CAS，synchronized本身是个锁升级的概念；
 * 3.ReentrantLock可以有tryLock()尝试锁控制锁不上怎么办。
 * 4.ReentrantLock可以有lockInterruptibly()，可以被打断。
 * 5.ReentrantLock可以进行公平锁可非公平锁的切换，在new ReentrantLock的时候传入true或者不传。
 * 6.synchronized是非公平锁。
 */


/**
 * ReentrantLock可以替代synchronized。
 * 由于m1锁定的是this，所以主方法里只有m1执行完的时候才能执行m2.
 * 使用reentrantLock必须要手动释放锁，而且必须要放在finally里面。
 * 使用synchronized如果遇到异常，JVM会自动释放锁。但是Lock必须调用lock.unlock()方法手动释放锁。 T02_ReentrantLock.java
 * <p>
 * 使用reentrantLock可以进行尝试锁定(tryLock)，这样无法锁定或者在指定时间内无法锁定。
 * T02_ReentrantLock.java  tryLock() ——> m3()
 * <p>
 * 使用reentrantLock还可以调用lockInterruptibly方法，可以对线程interrupt方法
 * 在一个线程等待锁的过程中，可以被打断。 T03_ReentrantLock.java
 * <p>
 * 当我们new一个ReentrantLock的时候可以传入一个true指定为公平锁，默认为非公平锁。
 * 公平锁的意思是谁等在前面就先让谁执行。
 * 公平锁：比如队列中有五个线程等待锁，这时候再新加入一个线程，这个新加入的线程会先检查队列中是否有已等待的线程，如果有会排到队列中最后面，等待前面都执行完才到它。
 * 不公平锁：不管队列中是否有线程，直接去抢锁，有可能就抢到了。     T04_ReentrantLock.java
 */

public class T01_ReentrantLock {

    synchronized void m1() {
        for (int i = 0; i < 10; i++) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(i);
            if(i%2 == 0) {
                m2();
            }
        }
    }

    synchronized void m2() {
        System.out.println("m2");
    }

    public static void main(String[] args) {
        T01_ReentrantLock t = new T01_ReentrantLock();
        new Thread((t::m1), "t1").start();
        new Thread((t::m2),"t2").start();
    }
}

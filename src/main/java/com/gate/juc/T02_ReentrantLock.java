package com.gate.juc;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

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
public class T02_ReentrantLock {

    Lock lock = new ReentrantLock();

    void m1() {
        lock.lock(); // synchronized(this) 加锁
        try {
            for (int i = 0; i < 10; i++) {
                System.out.println(i);
                TimeUnit.SECONDS.sleep(1);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // lock.unlock() 一定要放在finally里面。放在一定会执行的代码块里，不然如果try catch时卡住了，别人就拿不到这个锁了。
            lock.unlock(); // 解锁。用ReentrantLock时需要手动解锁。synchronized不需要手动解锁，大括号内容执行完毕会自动解锁。
        }
    }

    void m2() {
        try {
            lock.lock();
            System.out.println("m2");
        } finally {
            // lock.unlock() 一定要放在finally里面。放在一定会执行的代码块里，不然如果try catch时卡住了，别人就拿不到这个锁了。
            lock.unlock();
        }
    }

    /**
     * tryLock()
     * 使用tryLock()进行尝试锁定，不管锁定与否，方法都将继续执行。
     * 可以根据tryLock的返回值来判断是否锁定。
     * 也可以指定tryLock的时间，由于tryLock(time)抛出异常，所以要注意unLock()要写在finally内。
     */
    void m3() {
        boolean locked = false;
        try {
            locked = lock.tryLock(5, TimeUnit.SECONDS);
            System.out.println("m3" + locked);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (locked) lock.unlock();
        }
    }

    public static void main(String[] args) {
        T02_ReentrantLock t = new T02_ReentrantLock();

        new Thread((t::m1), "t1").start();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread((t::m2), "t2").start();

        new Thread((t::m3), "t3").start();
    }
}

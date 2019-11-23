package com.gate.juc;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static java.util.concurrent.TimeUnit.SECONDS;

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
public class T03_ReentrantLock {

    Lock lock = new ReentrantLock();

    void m1() {
        lock.lock();
        try {
            System.out.println("m1 start");
            SECONDS.sleep(Integer.MAX_VALUE);
            System.out.println("m1 end");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    void m2() {
        boolean locked = false;
        try {
            System.out.println("m2 start");
            lock.lockInterruptibly();
            System.out.println("m2 end");
        } catch (InterruptedException e) {
            System.out.println("interrupt");
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {

        T03_ReentrantLock t = new T03_ReentrantLock();
        new Thread(t::m1,"t1").start();
        Thread t2 = new Thread(t::m2);
        t2.start();
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t2.interrupt(); // 打断进程


    }
}

package com.gate.juc;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * ReadWriteLock:读写锁。
 * 共享锁和排他锁。Read就是共享锁，Write就是排他锁。
 * 如下面的代码：当main方法中传入的是ReentrantLock的lock时，7个读进程会按要求每隔一秒进行一个，
 *   7个都线程都读完后才可以进行写线程。
 *   当main方法中传入ReentrantReadWriteLock中的readLock和writeLock时，7个读线程会一秒内进行完，
 *   之后执行写线程。
 *
 * 当一个对象上上了一把读锁的时候，其他的线程也是可以读的，所以理论上上了读锁的对象被线程访问时是可以一起结束的。
 * Read读线程共享锁：在一个线程读的时候，其他的线程也可以继续。大大的提高了效率。
 *   如果在读的时候不加锁，会造成脏读的问题。
 * Write写线程排他锁：在一个线程写的时候，其他线程不可以写。
 */
public class T08_ReadWriteLock {

    static Lock lock = new ReentrantLock();
    private static int value;

    static ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    static Lock readLock = readWriteLock.readLock();
    static Lock writeLock = readWriteLock.writeLock();

    public static void read(Lock lock) {
        try {
            lock.lock();
            Thread.sleep(1000);
            System.out.println("read over");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public static void write(Lock lock, int v) {
        try {
            lock.lock();
            Thread.sleep(1000);
            value = v;
            System.out.println("write over");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        // 一秒一个
//        Runnable readR = () -> read(lock);
//        Runnable writeR = () -> write(lock, new Random().nextInt());
        // 一秒结束
        Runnable readR = () -> read(readLock);
        Runnable writeR = () -> write(writeLock, new Random().nextInt());

        for (int i = 0; i < 7; i++) {
            new Thread(readR).start();
        }
        for (int i = 0; i < 5; i++) {
            new Thread(writeR).start();
        }
    }

}

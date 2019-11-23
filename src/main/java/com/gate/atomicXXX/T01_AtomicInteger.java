package com.gate.atomicXXX;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * CAS: Compare And Swap. 叫无锁优化，就是自旋。
 * synchronized属于悲观锁：悲观的认为程序中的并发严重，所以严防死守。
 * CAS是乐观锁：CAS乐观的认为程序中的并发情况不那么严重，所以让线程不断去尝试。
 *
 * 由于某一些特别常见的操作，老是反反复复的加锁，加锁的情况下特别多，Java就提供了常见操作的类，这些类的内部自动带锁，
 * 自动带的锁并不是synchronized的锁，而是CAS，CAS号称无锁。
 * 凡是Atomic开头的包，AtomicXXX都是用CAS操作来保证线程安全的类。
 * AtomicXXX类本身方法都是原子性的，但不能保证多个方法连续调用是原子性的。
 * AtomicXXX里面调用的都是Compare And Swap.也就是CAS。
 * CAS用的是Unsafe类。
 * CAS实现方法:V代表要改的值，Expected代表期望的值，NewValue代表要改成什么值。
 *    cas(V, Expected, NewValue) {
 *        if V == E
 *        V = NEW
 *        otherwise try again or fail
 *    }
 *    如果V ！= Expected。代表线程在进入的时候，有人刚好改了V的值。比如V = 0, 线程一访问的时候V = 0, Expected = 0， NewValue = 1;
 *    当线程二刚好访问时V = 1，而此时线程二不知道线程一已经改了值，所以线程二的期望值还是0，也就是V = 0，Expected = 1，V != Expected，线程二
 *    会退出重新试。
 * <p>
 * ABA问题：在使用CAS的时候可能会引起ABA问题，也就是说：
 * 在一个线程做CAS操作的时候，当准备把Expected期望值变成NewValue的时候，这时另一个线程将V原始值改变后又改回来。
 * ABA就是一个值开始是A，然后被改成B，又被改回A。
 * 如果要解决这个问题，就加version，加版本号。ABA（A1.0，B2.0，A3.0），在对比的时候连着版本号一起对比。
 * AtomicStampedReference可以解决ABA问题。
 * ABA在基本数据类型没有问题，比如int、Long。但是如果ABA发生在引用上就会有问题。
 */
public class T01_AtomicInteger {

    // volatile int count = 0;
    AtomicInteger count = new AtomicInteger(0);

    /*synchronized*/ void m() {
        for (int i = 0; i < 10000; i++) {
            count.incrementAndGet(); //count++;
        }
    }

    public static void main(String[] args) {
        long startTime=System.currentTimeMillis();
        T01_AtomicInteger t = new T01_AtomicInteger();
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            threads.add(new Thread(t::m, "thread - "+i));
        }
        threads.forEach((o) -> o.start());

        threads.forEach((o) -> {
            try {
                o.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        System.out.println(t.count);
        long endTime=System.currentTimeMillis();
        long a = endTime - startTime;
        System.out.println("当前程序耗时："+ a +"ms");
    }
}

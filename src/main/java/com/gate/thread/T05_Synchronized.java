package com.gate.thread;

/**
 * synchronized关键字。
 * 为某个对象加锁。
 * 当多个线程访问同一资源的时候，就需要给资源上锁。
 * synchronized保证了原子性和可见性。
 * <p>
 * 加锁的方法和不加锁的方法是可以同时运行的。可能产生脏读问题。
 * <p>
 * synchronized和Exception一起时，如果出现异常，会自动释放锁。
 * 在并发处理过程中，有异常要小心处理，例如多个Servlet线程访问同一个资源，如果在第一个线程中抛出异常，锁被释放，其他线程就会进入同步代码区，有可能访问到异常时产生的数据。
 * 如果不想被释放，则需要加catch捕获异常，继续下面的代码。
 * <p>
 * synchronized(Object).Object不能用String常量，Integer，Long。
 * <p>
 * 锁定对象o，如果o的属性发生变化，不影响锁的使用。
 * 但是如果o编程另一个对象，则锁定的对象会发生改变。
 * 应该避免将锁定对象的引用变成另一个对象。
 * T14_SynchronizedChange.java
 */
public class T05_Synchronized {

    private static int count = 10;

    public void m() {
        // this 锁定当前对象
        synchronized (this) {  // 任何线程想要执行下面的代码，必须拿到this的锁。访问下面代码的时候需要看这把锁是不是归我这个线程所有，如果是就可以执行下面代码。
            count--;
            System.out.println(Thread.currentThread().getName() + " count = " + count);
        }
    }

    // synchronized方法和上面的this等值
    public synchronized static void m1() {
        count--;
        System.out.println(Thread.currentThread().getName() + " count = " + count);
    }

    public void m2() {
        synchronized (T05_Synchronized.class) {
            count--;
            System.out.println(Thread.currentThread().getName() + " count = " + count);
        }
    }
}

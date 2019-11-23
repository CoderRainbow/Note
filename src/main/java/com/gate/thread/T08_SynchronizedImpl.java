package com.gate.thread;

/**
 * synchronized的底层实现：
 * JDK早期的时候synchronized的实现是重量级的，synchronized都是要去找操作系统去申请锁，就会造成synchronized的效率非常低。
 * 后来的改进：synchronized是有锁升级的概念：
 * 当我们使用sync的时候，hotspot的实现流程：
 * 第一个去访问某把锁的线程（sync(Object)），先在Object的头上面markword记录里这个线程的ID（偏向锁，默认不会有第二个线程来抢这个锁）；
 * 如果有线程争用偏向锁，就从偏向锁升级为自旋锁，自旋锁默认旋十次；
 * 自旋锁旋十次以后如果还是得不到这把锁，升级为重量级锁，就是去操作系统那里申请资源。
 *
 * 执行时间短（加锁代码），线程数少，用自旋锁；
 * 执行时间长，线程数多，用系统锁。
 */


public class T08_SynchronizedImpl {
}

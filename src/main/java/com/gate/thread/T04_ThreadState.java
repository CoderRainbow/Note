package com.gate.thread;

/**
 * 线程的状态：线程的状态全部由JVM管理，JVM管理线程状态的时候也要通过操作系统，因为JVM是跑在操作系统的一个程序。
 * 1.new状态（新建状态）：当new一个线程的时候
 * 2.Runnable状态（可运行状态）：被线程调度器执行的时候，Runable有Ready就绪状态和Running执行状态，就绪状态就是在CPU的等待队列里，等待CPU运行；
 *   执行状态是CPU调用执行时。
 *   线程被调度器选中执行时，从就绪到执行状态。 Ready -> Running
 *   Thread.yield() 调用的时候会从执行状态跑到就绪状态。 Running -> Ready
 *   线程被挂起时会从执行状态跑到就绪状态。 Running -> Ready
 *   线程挂起就是CPU可能间隔运行线程，一会运行这个一会运行另一个，在运行这个线程的时候，另一个线程就是挂起状态。
 * 3.TimedWaiting状态（等待状态）：调用sleep(time)、wait(time)、join(time)、parkNanos()、parkUntil()会按照时长等待，时间结束后自动回到Runnable状态。
 * 4.Waiting状态（等待状态）：在运行的时候调用 wait()、join()、park()会进入Waiting状态，在Waiting状态调用notify()、notifyAll()、unpark()会进入Runnable状态。
 * 5.Blocked状态（阻塞状态）：加上同步代码块，进入同步代码块没有得到锁的时候就是阻塞状态，获得锁的时候跑到就绪状态允运行。
 * 6.Terminated状态（结束状态）：线程顺利执行完进入结束状态。线程结束后不允许调用start方法进入Runnable状态。
 * 7.Interrupt状态（打断状态）：在线程运行时打断状态会出现一个interrupted Exception异常，捕获这个异常，可以选择是否继续执行代码。
 *    一般不用Interrupt来控制业务逻辑。
 */

public class T04_ThreadState {

    public static void main(String[] args) {

        Thread thread = new Thread(()->{
            for (int i = 0; i < 5; i++) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(i);
            }
        });
        // 通过getState()方法得到线程的状态
        System.out.println(thread.getState());  // New
        thread.start();
        System.out.println(thread.getState());  // Runnable
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(thread.getState());  // Terminated
    }
}

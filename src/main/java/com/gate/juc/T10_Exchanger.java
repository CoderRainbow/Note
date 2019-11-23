package com.gate.juc;

import java.util.concurrent.Exchanger;

/**
 * Exchanger:交换器，两个线程之间交换数据。
 */
public class T10_Exchanger {

    static Exchanger<String> exchanger = new Exchanger<>();

    public static void main(String[] args) {

        new Thread(() -> {
            String s = "t1";
            try {
                s = exchanger.exchange(s);
                // 第一个参数，要交换的对象。
                // 第二个参数，等多久交换不成功就不交换了。
//                s = exchanger.exchange(s, 3, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } /*catch (TimeoutException e) {
                e.printStackTrace();
            }*/
            System.out.println(Thread.currentThread().getName() +":"+ s);
        },"t1").start();

        new Thread(() -> {
            String s = "t2";
            try {
//                TimeUnit.SECONDS.sleep(5);
                s = exchanger.exchange(s);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() +":"+ s);
        },"t2").start();
    }
}

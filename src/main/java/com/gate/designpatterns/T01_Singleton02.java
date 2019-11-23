package com.gate.designpatterns;

/**
 * 懒汉式
 * 在用的时候再初始化。线程不安全,如果需要线程安全，就需要加锁synchronized。
 */
public class T01_Singleton02 {

    private static T01_Singleton02 INSTANCE;

    private T01_Singleton02(){};

    // 如果不加synchronized，线程是不安全的
    public static synchronized T01_Singleton02 getInstance(){
        if(INSTANCE == null) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            INSTANCE = new T01_Singleton02();
        }
        return INSTANCE;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                System.out.println(T01_Singleton02.getInstance().hashCode());
            }).start();
        }
    }

}

package com.gate.thread;

/**
 * 懒汉式
 */
public class T11_Singleton02 {

    private static T11_Singleton02 INSTANCE;

    private T11_Singleton02(){};

    // 如果不加synchronized，线程是不安全的
    public static synchronized T11_Singleton02 getInstance(){
        if(INSTANCE == null) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            INSTANCE = new T11_Singleton02();
        }
        return INSTANCE;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                System.out.println(T11_Singleton02.getInstance().hashCode());
            }).start();
        }
    }

}

package com.gate.designpatterns;

/**
 * 静态内部类的方式
 * 最完美的单例模式
 *
 * 加载外部类的时候不会加载静态内部类，这样可以实现懒加载
 * JVM保证单例，JVM一个类只加载一次，所以不用加锁，JVM会保证这个类只被加载一次。
 */
public class T01_Singleton04 {

    private T01_Singleton04() {

    }

    private static class T0104Holder {
        private final static T01_Singleton04 INSTANCE = new T01_Singleton04();
    }

    // 调用getInstance()的时候上面的静态内部类才会加载。
    public static T01_Singleton04 getInstance() {
        return T0104Holder.INSTANCE;
    }

    public void m() {
        System.out.println("m");
    }

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                System.out.println(T01_Singleton04.getInstance().hashCode());
            }).start();
        }
    }
}

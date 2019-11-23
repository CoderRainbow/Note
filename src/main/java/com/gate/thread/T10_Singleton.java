package com.gate.thread;

/**
 * 单例模式：保证在JVM内存中，永远只有某一个类的一个实例。在工程中有一些类没有必要new好多个对象，比如权限管理者，就是专门管权限的，再比如守大门的。
 * 饿汉式：
 * 类加载到内存后，就实例化一个单例，JVM保证线程安全。
 * 唯一缺点：不管用到与否，类加载的时候就会实例化。
 * 懒汉式：
 * 在用的时候再初始化。线程不安全,如果需要线程安全，就需要加锁synchronized。见T11_Singleton02.java
 */
public class T10_Singleton {

    // 定义一个类的对象
    private static final T10_Singleton INSTANCE = new T10_Singleton();

    // private构造方法，别人没法new。
    // 既然别人没法new，我在上面自己new了，理论上整个程序中就只有我这一个实例。
    private T10_Singleton(){};

    // 通过getInstance来获得实例。
    public static T10_Singleton getInstance() {
        return INSTANCE;
    }

    public void m() {
        System.out.println("m");
    }

    // 无论调用多少次getInstance本质上只有一个对象。
    public static void main(String[] args) {
        T10_Singleton t1 = T10_Singleton.getInstance();
        T10_Singleton t2 = T10_Singleton.getInstance();
        System.out.println(t1 == t2);
    }
}

package com.gate.designpatterns;

/**
 * Singleton
 * 单例模式：保证在JVM内存中，永远只有某一个类的一个实例。在工程中有一些类没有必要new好多个对象，比如权限管理者，就是专门管权限的，再比如守大门的。
 * 饿汉式：
 * 类加载到内存后，就实例化一个单例，JVM保证线程安全。
 * 唯一缺点：不管用到与否，类加载的时候就会实例化。
 */
public class T01_Singleton01 {
    // 这个写法是线程安全的，因为static定义的INSTANCE是在类加载的时候就实例化的，JVM保证一个对象只被实例化一次。
    // 定义一个类的对象
    private static final T01_Singleton01 INSTANCE = new T01_Singleton01();

    // private构造方法，别人没法new。
    // 既然别人没法new，我在上面自己new了，理论上整个程序中就只有我这一个实例。
    // 程序外部想调用只能：T01_Singleton01 t = T01_Singleton01.getInstance(); 这样就保证不管是谁拿到的都是上面定义的那个INSTANCE.
    private T01_Singleton01(){};

    // 通过getInstance来获得实例。
    public static T01_Singleton01 getInstance() {
        return INSTANCE;
    }

    public void m() {
        System.out.println("m");
    }

    // 无论调用多少次getInstance本质上只有一个对象。
    public static void main(String[] args) {
        T01_Singleton01 t1 = T01_Singleton01.getInstance();
        T01_Singleton01 t2 = T01_Singleton01.getInstance();
        System.out.println(t1 == t2);
    }
}

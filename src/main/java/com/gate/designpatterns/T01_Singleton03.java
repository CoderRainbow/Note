package com.gate.designpatterns;

/**
 * Double Check Lock：双重检查锁
 * 双重检查锁、双重检查单例，适合用在单例里有业务逻辑的时候。
 * 双重检查单例和双重检查锁要不要加volatile？
 *   要加，如果不加volatile，会出现指令重排序问题。
 *   在INSTANCE = new T01_Singleton03(); 也就是实例化时，在经过JVM编译的时候分成三步：
 *   1.申请内存
 *   2.初始化成员变量
 *   3.把内存的内容赋值给INSTANCE
 *   有可能出现一个对象初始化一半的时候就赋值给变量了。
 *   1.a = 0:分配内存，默认值
 *   2.a = 8:初始化:初始化值
 *   3.a = 8:把内存中的值赋值给变量
 *   在指令重排序的情况发生的时候，第二个线程进来可能正在a = 0的阶段，既然a != null，所以第二个线程拿到的就是0，是错误的。
 *   配合下面的代码看上面的注释。
 */
public class T01_Singleton03 {

    private static volatile T01_Singleton03 INSTANCE;

    private T01_Singleton03(){};

    // 双重检查单例：防止里面会有业务代码，如果有业务代码且锁加在方法上是不合适的。所以改成下面的样子。
    private T01_Singleton03 getINSTANCE() {
        // 可以在这里写业务代码
        if(INSTANCE == null){
            synchronized (T01_Singleton03.class) {
                if(INSTANCE == null) {
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    INSTANCE = new T01_Singleton03();
                    for (int i = 0; i < 3; i++) {
                        System.out.println(INSTANCE.hashCode());
                    }
                }
            }
        }
        return INSTANCE;
    }

    public static void main(String[] args) {
        T01_Singleton03 s1 = new T01_Singleton03().getINSTANCE();
        T01_Singleton03 s2 = new T01_Singleton03().getINSTANCE();
        System.out.println(s1 == s2);
    }
}

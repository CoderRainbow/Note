package com.gate.designpatterns;

/**
 * 枚举单例
 * 不仅可以解决线程同步，还可以防止反序列化
 * 枚举类之所以可以防止反序列化，是因为枚举类是没有构造方法的，就算你拿到了class文件，也没有办法构造它的对象。
 *
 * enum:枚举
 */
public enum T01_Singleton05 {

    // 定义枚举变量
    INSTANCE;

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                // 使用枚举变量时只需要 类名.变量名 就好了
                System.out.println(T01_Singleton05.INSTANCE.hashCode());
            }).start();
        }
    }
}

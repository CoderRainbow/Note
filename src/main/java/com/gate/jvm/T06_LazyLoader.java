package com.gate.jvm;

/**
 * LazyLoader:懒加载
 * 严格来讲是LazyInitializing懒初始化。
 * JVM规范并没有规定何时加载。
 * 但是严格的规定了什么时候必须初始化。
 *    new、getstatic、putstatic、invokestatic指令时，必须初始化类，访问final变量除外。
 *    java.lang.reflect对累进行反射调用时。
 *    初始化子类的时候，父类首先初始化。
 *    虚拟机启动时，被执行的主类必须初始化。
 */
public class T06_LazyLoader {

    public static void main(String[] args) throws ClassNotFoundException {
//        P p; // 这个时候类不会被加载。
//        X x = new X(); // X entends了P，所以P会被打印
//        System.out.println(P.i);  // 静态final常量，所以加载的时候不会加载P。
//        System.out.println(P.j); // 不是final的，所以会加载P
        Class.forName("com.gate.jvm.T06_LazyLoader$P"); // 外部类$内部类可以加载内部类

    }
    public static class P {
        final static int i = 8;
        static int j = 9;
        static {
            System.out.println("P");
        }
    }

    public static class X extends P {
        static {
            System.out.println("X");
        }
    }
}

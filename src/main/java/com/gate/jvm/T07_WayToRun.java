package com.gate.jvm;

/**
 * Java混合执行。
 * 一个class文件load到内存通过解释器来执行。
 * Java有一个JIT，Just In Time。JIT编译器，指有些代码需要编译成本地代码来执行，相当于exe，这种情况下用JIT。
 * Java默认情况下是混合模式：
 *   1.混合使用解释器 + 热点代码编译。
 *      一段代码，刚开始由解释器执行，在整个虚拟机执行过程中，有某段代码，某个循环等，Java虚拟机发现
 *      这个循环执行的频率特别高，一秒钟执行好多次，解释器解释来解释去发现这段代码总被人用，就会用JIT
 *      将这段代码编译成本地代码， 将来执行这段代码就会执行本地代码不会再用解释器解释执行，提升效率。
 *   2.可以用参数配置的形式指定要求用什么模式：
 *      -Xmixed：是混合模式。开始解释执行，启动速度较块，对热点代码实行监测和编译。
 *      -Xint：使用纯解释模式，启动很快执行稍慢。
 *      -Xcomp：使用纯编译模式，执行很快，启动很慢。
 *   Q：怎么确定热点代码：
 *   A：引入方法计数器，监测方法执行频率；循环计数器，监测循环执行频率。HotSpot.
 *
 *   Q：监测热点代码参数：-XX:CompileThreshold=10000
 */
public class T07_WayToRun {

    public static void main(String[] args) {
        for (int i = 0; i < 10000; i++) {
            m();
        }
        long start = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            m();
        }
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }
    public static void m() {
        for (long i = 0; i < 10000L; i++) {
            long j = i%3;
        }
    }
}

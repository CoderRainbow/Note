package com.gate.jvm;

/**
 * JVM:Java虚拟机。
 * JVM是一种规范，定义了JVM应该能够执行什么，应该具备哪些模块，遇到什么指令该做什么事情。
 * JVM是虚构出来的计算机，有自己的CPU、字节码指令集（汇编语言）、内存管理（堆、栈、方法区）。
 *
 * Java从编码到执行：
 * x.java ->(javac)-> x.class ->(java)-> ClassLoader ->(Java类库)-> 字节码解释器、JIT即时编译器 -> 执行引擎 -> OS硬件。
 * 从使用java命令调用ClassLoader开始就是JVM。
 * 任何语言只要能编译成class，符合class的规范，就能在JVM虚拟机上跑。
 * Q：Java是解释运行还是编译运行的。 A:是混合的，字节码解释器和JIT即时编译器。
 *
 * JDK JVM JRE:
 * JVM:Java虚拟机。
 * JRE:运行时环境，包含JVM和核心类库core lib。
 * JDK:Java开发环境，包含JRE、JVM和开发工具。
 *
 * JVM的实现：
 *      PS C:\Users\w> java -version
 *      java version "1.8.0_202"
 *      Java(TM) SE Runtime Environment (build 1.8.0_202-b08)
 *      Java HotSpot(TM) 64-Bit Server VM (build 25.202-b08, mixed mode)
 *      HotSpot就是JVM的实现。 mixed和mode是代表解释运行和编译运行混合。
 */


public class T01_JVMBasic {

    public static void main(String[] args) {
        System.out.println("1");
    }
}

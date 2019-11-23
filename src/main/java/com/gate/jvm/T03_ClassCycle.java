package com.gate.jvm;

/**
 * ClassCycle: class周期。
 *
 * class -> loading -> linking(verification -> preparation -> resolution) -> initializing -> gc.
 *  1.loading: 把一个class文件load到内存。class本来是一个二进制文件一个一个字节，把这些装进内存。
 *  2.linking:
 *      verification: 校验。校验load的class文件是否符合标准。比如java编译成的class前面四个字节是CA FE BA BE。
 *      preparation: 把class的静态变量赋默认值。比如static int i = 8，在这里并不是把i的值直接赋成8，而是先赋成默认值0.
 *      resolution: class文件里的常量池里面用到的符号语言转换成为直接的内存地址，可以直接访问到。
 *  3.initializing: 静态变量赋值，比如上面的i，赋值为初始值8，调用静态代码块。
 *  4.gc:
 *  一个class被load到内存后，会创建两块内容：一个是把class的二进制东西放到内存中，第二个生成了class类的对象，这个class的对象指向了这块内存。
 *
 *
 * 类加载器：ClassLoader
 * JVM有不同的类加载器层次，分别加载不同的class。JVM所有的class都是被类加载器加载到内存的。
 * ClassLoader是反射的基石，平时通过ClassLoader返回的类的对象去查询二进制文件。
 */
public class T03_ClassCycle {
}

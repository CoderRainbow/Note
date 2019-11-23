package com.gate.jvm;

/**
 * Class File Format
 * Class文件就是Java文件编译后的文件。x.java ->(javac)-> x.class.
 * java文件编译成class文件，再反编译后，会自动添加一个无参构造器。
 *
 * Class文件是一个二进制的字节流。
 * IDEA查看十六进制和二进制class文件插件：BinEd
 * Class文件内容：(16进制例)
 *   Magic Number : 头部前八位。
 *   Minor Version -小版本:
 *   Major Version -大版本:
 *   Constant_pool_count -常量池相关:
 *   Constant_pool -常量池具体实现:
 *   access_flags -class的修饰符:
 *   this_class -当前类名:
 *   super_class -父类名:
 *   interfaces_count -实现了几个接口:
 *   interfaces -具体实现的接口:
 *   fields_count -有多少属性:
 *   fields -具体的属性:
 *   methods_count 有-多少方法:
 *   methods -具体方法:
 *   attributes_count-u2 -有多少附加属性:
 *   attributes -附加属性具体信息:
 *
 *   鼠标放在java文件的class上，view-show ByteCode With jclasslib可以看到class文件上面内容的信息。
 */




public class T02_class {
}

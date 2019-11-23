package com.gate.jvm.optimization;

/**
 * JVM命令行参数参考：https://docs.oracle.com/javase/8/docs/technotes/tools/unix/java.html
 *
 * HotSpot参数分类：
 *   标准的参数：-开头，所有版本HotSpot都支持。比如java -version就是标准参数。
 *   非标准： -X开头，特定版本的HotSpot支持的特定指令。java -X会打印出所有-X开头的。
 *   不稳定： -XX开头，下个版本可能会取消。
 *
 * OOM(OutOfMemoryError)内存溢出问题：
 *   1.首先查看堆内存是多大：java -XX:+PrintCommandLineFlags 程序名。
 *     可以查看到:
 *       -XX:InitialHeapSize=起始堆大小、 -XX:MaxHeapSize=最大堆大小
 *     指定堆内存大小：java -Xms:指定最小值，-Xmx:指定最大值，-Xmn:指定新生代大小。
 *       例：java -Xmn10M -Xms40M -Xmx60M -XX:+PrintCommandLineFlags 程序名。
 *   2.指定垃圾回收器组合：
 *      ↓ 指定新生代 Serial和老年代Serial Old。适合小型程序，默认情况下不会是这种情况，HotSpot会根据计算及配置和JDK版本自动选择收集器组合。
 *      -XX:+UseSerialGC = Serial New(DefNew) + Serial Old
 *      ↓ 指定新生代ParNew，老年代CMS，Serial Old，这个组合会产生很多碎片，当碎片化过度的时候会产生FullGC，CMS的FullGC是使用单线程的Serial Old，当内存大的时候会非常慢。
 *      -XX:+UseConcurrentMarkSweepGC = ParNew + CMS + Serial Old
 *      ↓ 指定新生代Parallel Scavenge，老年代Parallel Old。 jdk1.8默认的垃圾收集器组合。生产环境大多数都是这个。
 *      -XX:+UseParallelGC = Parallel Scavenge + Parallel Old
 *      -XX:+UseParallelOldGC = Parallel Scavenge + Parallel Old
 *      -XX:+UseG1GC = G1
 *   3.-XX:+PrintFlagsFinal|grep xxx 找到对应参数
 *     -XX:+PrintFlagsFinal 查询最终参数值大小
 *     -XX:+PrintFlagsInitial 查询默认参数值大小
 *
 * tuning调优:
 *   调优从规划开始，从具体的业务场景开始。
 *   调优步骤：
 *      1.熟悉业务场景。比如到底选择什么样的机器，我到底是选择响应时间优先还是吞吐量优先。
 *      2.选择垃圾回收器组合。
 *      3.计算内存需求。
 *      4.设定年代大小、升级年龄。
 *      5.设置日志参数。
 *        ↓ 表明我要记录日志文件，%t是当前时间。        ↓ 指定日志文件循环使用       ↓ 一共会产生5个日志文件，第6个覆盖第一个，循环使用。
 *        -Xloggc:/opt/xxx/logs/xxx-xxx-gc-%t.log -XX:+UseGCLogFileRotation -XX:NumberOfGCLogFiles=5
 *        ↓ 每个日志文件最大大小                       ↓ GC产生时间点            ↓ GC是为何产生的
 *        -XX:GCLogFileSize=20M -XX:+PrintGCDetails -XX:+PrintGCDateStamps -XX:+PrintGCCause
 *      6.观察日志情况。
 *    调优例子：
 *      场景：垂直电商，最高每日百万订单，处理订单系统需要怎样的服务器配置？
 *      1.问清最高峰值，是百万订单平均分在24小时内，还是某一小时比较多。假设一小时36万，每秒要处理100单。
 *      2.需要计算出处理一张订单需要多长时间。假设0.05s一单，一秒钟20单。
 *      3.实际情况一秒20单，但是要求一秒100单，所以需要加机器到5台。如果使用1台机器，也可以实现，但是发生FullGC的可能性比较高。
 *      4.需要计算出20个订单需要占多少内存。需要保证Eden区可以装下20个订单。也就是处理订单的速度快于新增订单的速度。
 *      5.到底设置多少内存取决于能容忍的YGC和FGC的频率。一般上线后通过后台日志的监测，来调整内存。
 *      6.设定日志文档参数。日志文件生成名字为gc-%t.log.0.current；current代表当前正在使用，用more打开
 *      7.如果出问题了根据日志输出，读日志来确定问题。
 *
 *
 * ps:系统宕机要看内存转储文件。导出内存看内存里是什么情况导致的宕机。
 *
 * Q: 如果再用Parallel Scavenge + Parallel Old组合，怎么做才能让系统基本不产生FGC？
 * Q: 如果再用ParNew + CMS组合，怎么做才能让系统基本不产生FGC？
 * A: 1.加大JVM内存
 *    2.加大Young的比例
 *    3.提高Y-O年龄
 *    4.提高S区的比例
 *    5.避免代码内存泄漏
 *
 * Q: G1是否分代？G1垃圾回收器会产生FGC吗？
 * A: G1将内存分为一块一块的内存，分成一个一个的Region(区域)，这里是有新生代和老年代的，有的Region属于新生代，有的Region数据老年代。
 *    在不同的Region里算法不同，还有的属于Survivor.
 *    G1垃圾回收器会产生FGC。当它的回收频率比产生新对象的内存慢的时候，回收的速度赶不上分配的速度，这时会产生FGC。
 *    G1会多线程的同时在整个内存区域里进行FGC。
 *    如果G1产生FGC，应该扩大内存(存放的多)，提高CPU性能(回收的快)。
 */



public class T02_JVMOptimizationActual {
}

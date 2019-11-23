package com.gate.jvm.optimization;

/**
 * CMS(Concurrent Mark Sweep):
 * CMS收集器是一种以获取最短回收停顿时间为目标的收集器，适用于集中在互联网站或者B/S系统的服务端的Java应用。
 * CMS收集器是基于"标记-清除"算法实现的，可以跟新生代的Parallel New、Serial搭配使用。
 *
 * CMS只会回收老年代和永久代的垃圾，不会收集年轻代。
 * CMS是一种预处理垃圾回收器，它不能等到old内存用尽时回收，需要在内存用尽之前，完成回收操作，否则会导致并发回收失败，
 *   所以CMS垃圾回收器开始执行回收操作有一个阈值，JDK6以上默认是92%，JDK6之前默认是68%.
 * CMS不会移动对象以保证空间的连续性，相反，CMS保存所有空闲内存片段的列表，通过这样的方式，CMS可以避免为存货对象重新
 *   分配位置引起的开销，但是相应的会引起内存的碎片化。
 *
 * CMS的执行步骤：
 *  1.CMS Initial Mark:初始标记。标记GC ROOT能直接关联到的对象，也就是根对象。会产生STW。
 *  2.CMS Concurrent Mark:并发标记。由当前阶段标记过的对象触发，所有可达的对象都在本阶段标记。不会产生STW。
 *  3.CMS Concurrent Preclean:并发预处理。标记从新生代晋升到老年代的对象，以及在并发阶段被修改了的对象。不会产生STW。
 *  4.CMS Remark:重新标记。重新扫描堆中的对象，进行可达性分析，标记活着的对象。会产生STW。
 *  5.CMS Concurrent Sweep:并发清理。回收在对象图中的不可达对象。不会产生STW。
 *  6.CMS Concurrent Reset:并发重置。做一些收尾工作，以便下一次GC。不会产生STW。
 *  CMS只会在初始标记和重新标记阶段产生STW。剩下阶段都是并发的。
 *
 * CMS优化：
 * 1.一般CMS的GC耗时80%都在remark阶段，也就是重新标记阶段，如果发现remark阶段停顿时间很长，可以添加-XX:+CMSScavengeBeforeRemark参数
 *  代表在进行CMS之前先进行一次YGC。
 * 2.CMS是基于标记-清除算法的，只会将标记为未存活的对象进行删除，并不会移动对象整理内存空间，会造成内存碎片，这时候我们可以添加这个参数：
 *   -XX:CMSFullGCsBeforeCompaction=n
 *   CMS GC要决定是否在Full GC的时候进行压缩。
 * 3.执行CMS GC的过程中，同时业务线程也在运行，当新生代空间满了，执行YGC的时候，需要将存活的对象放入老年代，而此时老年代空间不足，这时CMS还没有
 *   回收老年代，或者在做YGC的时候新生代空间放不下需要放入老年代，而老年代也放不下的时候会产生：
 *   Concurrent mode failure。
 *   解决：要确定发生Concurrent mode failure的原因是因为碎片造成的，还是Eden区有大对象直接晋升老年代造成的，一般有大量的对象晋升老年代容易导致
 *   这个错误，这种是存在优化空间的，要保证大部分对象尽可能的在新生代GC掉。
 * 4.CMS默认启动的回收线程数目是(ParallelGCThreads + 3)/4.这里的ParallelGCThreads是新生代的并行收集线程数。
 *   新生代的并行收集线程数默认是(ncpus ≤ 8)？ncpus:3+((ncpus*5)/8)，可以通过-XX:ParallelGCThreads=N 来调整，如果要直接设定CMS回收线程数
 *   可以通过-XX:ParallelCMSThreads= n，注意这个n不能超过cpu线程数，需要注意的是增加GC线程数，就会和应用争抢资源。
 */




public class T04_CMS {
}

package com.gate.jvm.optimization;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * JVMOptimizationTool JVM分析工具
 *
 * GC日志解读：
 *  [GC             --代表YGC. 因为下面有DefNew是年轻代。
 *  (Allocation Failure)       --产生YGC的原因
 *  [DefNew:4544K -> 256K(6144K),0.0873295 secs]      --4544k：回收前新生代大小，259：回收后新生代大小，6144K新生代总大小。
 *  4544K -> 4356K(19840K),0.0837310 secs]     --4544k：回收前堆占用空间，4356K：回收后堆占空间，19840K堆总大小。
 *  [Times:user=0.00 sys=0.09,real=0.09 secs]     --用户执行了多长时间，内核执行了多长时间，真实执行了多长时间。
 *
 * Heap dump部分解读：
 *  eden space 5632k 94% used[0x0000000000ff9800000,0x0000000000ffeb3e28,0x0000000000fff00000]
 *  eden区     总内存 已使用94% 起始地址                使用空间结束地址        整体空间结束地址
 *
 * 分析工具：
 *  1.执行：java -Xms200M -Xmx200M com.gate.jvm.optimization.T03_JVMOptimizationTool
 *  2.执行top命令进行监控：可以发现占用CPU和内存的程序的PID，发现那些进程比较吃内存和CPU。比如发现内存不断增长，CPU占用居高不下。
 *  3.执行JPS命令：可以看到哪个正在运行的程序是top发现的有问题的PID。可以定位到有问题的具体的Java进程。
 *  4.执行jstat命令：jstat -gc pid 500: jstat每隔500ms打印一次这个pid的gc情况。
 *                 jstat -gccapacity pid: 可以显示JVM内存中三代(young,old,perm)对象的使用和占用大小情况。
 *  5.执行jmap命令：jmap会显示内存情况。
 *                jmap -histo pid:显示程序内每个方法占用的字节，占用的对象等。
 *                jmap可以把整个堆内存内容全部倒出来，分析文件。
 *  6.JConsole:一个可以远程监控的平台。
 *             配置JConsole: 1.程序启动时加入参数。 要开启jmx，一个远程扩展的类库。sun.management.jmxremote.port=11111/authenticate=false/ssl=false
 *                          2.修改/etc/hosts文件
 *                          3.关闭Linux防火墙
 *                          4.连接
 *  7.Arthas 阿尔萨斯: 1.java -jar arthas-boot.jar.
 *                    2.第1步启动后，它会提示你发现了一些Java进程，选择一个要监控的。
 *                    3.选择要监控的程序后，Arthas会启动。
 *                    4.执行jvm命令，就可以看到JVM的基本信息和使用的垃圾回收器等。
 *                    5.执行dashboard命令，可以查看某个线程占用的CPU和内存的情况。会有CPU占用的情况，以及状态，WAITING还是RUNNABLE等。还有堆内存情况等。
 *                    6.执行sc命令，可以查看JVM已加载的类的信息，有哪些类，有哪些对象。
 *
 * 8.出问题的解决办法：
 *     1.在启动的时候加入参数： -XX:+HeapDumpOnOutOfMemoryError. 这个参数意思是当我们有OOM错误出现的时候，会把整个堆的内存dump出来形成文件方便分析。
 *     2.或者用jamp -dump:format=b,file=thisClass.dump pid也可以生成一个dump文件。
 *     3.使用jhat分析dump文件：jhat thisClass.dump。默认会在7000端口开一个web。
 *     4.访问127.0.0.1:7000查看jhat分析出的结果。查看哪个对象太多把内存撑爆。Instance counts for all classes里查看。
 *
 *
 */
public class T03_JVMOptimizationTool {

    private static class CardInfo {
        BigDecimal price = new BigDecimal(0.0);
        String name = "张三";
        int age = 5;
        Date birthdate = new Date();

        public void m() {

        }
    }

    private static ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(50,
            new ThreadPoolExecutor.DiscardOldestPolicy());

    public static void main(String[] args) throws InterruptedException {
        executor.setMaximumPoolSize(50);

        for(;;) {
            modeFit();
            Thread.sleep(100);
        }
    }

    private static void modeFit() {
        List<CardInfo> taskList = getAllCardInfo();
        taskList.forEach(info -> {
            executor.scheduleWithFixedDelay(() -> {
                info.m();
            }, 2,3, TimeUnit.SECONDS);
        });
    }

    private static List<CardInfo> getAllCardInfo() {
        List<CardInfo> taskList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            CardInfo ci = new CardInfo();
            taskList.add(ci);
            System.out.println(ci);
        }
        return taskList;
    }

}

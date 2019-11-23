package com.gate.jvm;

/**
 * 类加载器：ClassLoader
 * JVM有不同的类加载器层次，分别加载不同的class。JVM所有的class都是被类加载器加载到内存的。
 * 一个class被load到内存后，会创建两块内容：一个是把class的二进制东西放到内存中，第二个生成了class类的对象，这个class的对象指向了这块内存。class对象在Metespace中。
 *   通过class的对象可以访问里面的方法，但是我们不能直接访问二进制的内容。
 *
 * ClassLoader  load的过程是模板方法。在父类中已经把所有逻辑定义好了，只有中间这部分没有实现交给自己的类去实现。
 *
 * 类加载器层次：过程：
 * 不同的类加载器负责加载不同的class。
 * 不同的类加载器负责加载不同的class来源于Launcher源码。
 * main(2.0 code)的代码可以测试出哪些jar被哪些ClassLoader加载。
 *   1.Bootstrap：负责加载JDK里面最核心的jar里面的内容。比如runtime.jar、charset.jar等。 c++实现。当getClassLoader()拿到空值时，代表到了这层加载器。
 *   2.Extension：扩展类加载器。负责加载扩展包里的文件。比如jre/lib/ext/*.jar。
 *   3.APP：平时类用到的加载器。负责加载classpath的指定内容。比如我们平时写的类。
 *   4.Custom ClassLoader：自定义ClassLoader。
 *     每个加载器都有自己的缓存，自己负责哪部分就加载哪部分，缓存可以理解为它们自己内部的一个数组等。
 *     父加载器并不是类加载器的加载器，也不是类加载器的父类加载器。
 *     父加载器只是加载器的上层加载器，只是语法上的父子关系，并不是继承关系。
 *     所有类加载器的加载器都是BootStrap.
 *     自顶而下进行实际查找和加载child方向；
 *     自下而上检查该类是否已经加载parent方向。
 *     如果4找不到这个类有没有加载，就委托给3这个父加载器。
 *     3是4的父加载器、2是3的父加载器、1是2的父加载器。但它们4个不是继承关系。
 *
 * 双亲委派：
 *    一个class要load到内存的顺序：
 *    如果自定义了，就现在自定义里找是否加载了，如果加载了就返回，如果没有加载它不会直接加载，它会去找上一层也就是APP层找是否加载了这个class，
 *    如果有就返回，如果没有继续委托到上一层找，如果找到最上一层还没有加载这个class，它会一层一层往下通知最后委托到自定义加载器加载。
 *    如果最后自定义加载器加载成功，则加载成功，如果未加载成功，就抛异常ClassNotFound Exception.
 *    双亲委派机制源码，ClassLoader.java -> protected Class<?> loadClass(String name, boolean resolve)
 *
 * 为什么类加载采用双亲委派机制：
 *    1.为了安全，比如在自定义ClassLoader时自定义一个java.lang.String。打包成类库提交给客户，客户输入的密码等信息就可以通过代码控制以发邮件等方式得到。
 *      如果使用双亲委派，在ClassLoader加载java.lang.String的时候就会向上查父加载器，查到最顶层的时候发现BootStrap加载器已经加载了，就不会加载自定义的。
 *      ClassLoader.java中的源码里，每个加载器的parent都是final的，不能修改的，所以必须要经过这个流程。
 *    2.也防止类重复加载，减少资源。
 *
 *
 * 类加载器的范围：
 *
 */
public class T04_ClassLoaderLevel {

    public static void main(String[] args) {
        // Bootstrap加载器加载。因为BootStrap是C++实现的一个模块，Java里没有class和它去直接对应，所以当我们getClassLoader() get到最顶层时，就是空。
        System.out.println(String.class.getClassLoader());
        System.out.println(String.class.getClass().getClassLoader());
        System.out.println(sun.net.spi.nameservice.dns.DNSNameService.class.getClassLoader()); // Extension加载器
        // 这个ClassLoader的ClassLoader是Bootstrap，所以是空
        System.out.println(sun.net.spi.nameservice.dns.DNSNameService.class.getClassLoader().getClass().getClassLoader());
        System.out.println(T04_ClassLoaderLevel.class.getClassLoader()); // APP加载器
        // .getParent() get父加载器
        /* sun.misc.Launcher$ExtClassLoader@49476842
        sun.misc.Launcher 是包， ExtClassLoader是Launcher的内部类，@49476842是这个类的哈希值 */
        System.out.println(T04_ClassLoaderLevel.class.getClassLoader().getParent()); // Extension加载器
        // 这个ClassLoader的ClassLoader是Bootstrap，所以是空
        System.out.println(T04_ClassLoaderLevel.class.getClassLoader().getClass().getClassLoader());

        ////////////////////////////////////////////////////////////////////////////////////////////

        // 2.0 code
        // 可以看到哪些jar包是被哪些ClassLoader加载的
        System.out.println("pathBoot ----------------------↓");
        String pathBoot = System.getProperty("sun.boot.class.path");
        System.out.println(pathBoot.replaceAll(";", System.lineSeparator()));
        System.out.println("pathExt ----------------------↓");
        String pathExt = System.getProperty("java.ext.dirs");
        System.out.println(pathExt.replaceAll(";", System.lineSeparator()));
        System.out.println("pathApp ----------------------↓");
        String pathApp = System.getProperty("java.class.path");
        System.out.println(pathApp.replaceAll(";", System.lineSeparator()));

    }
}

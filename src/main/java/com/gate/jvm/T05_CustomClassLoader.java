package com.gate.jvm;

import java.io.*;

/**
 * CustomClassLoader：自定义加载器。
 * 自定义ClassLoader继承ClassLoader并重写findClass(String name).
 */
public class T05_CustomClassLoader extends ClassLoader{

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        File f = new File("E:\\Gate\\Gate\\Thread\\target\\classes\\com\\gate\\jvm",
                name.replaceAll(".","/").concat(".class"));
        try {
            FileInputStream fis = new FileInputStream(f);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int b = 0;
            while ((b=fis.read()) !=0 ){
                baos.write(b);
            }
            byte[] bytes = baos.toByteArray();
            baos.close();
            fis.close();
            // 把一个二进制的流转化成class对象
            return defineClass(name, bytes, 0, bytes.length);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return super.findClass(name);
    }

    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        // 调用ClassLoader的loadClass的方法就能把类加载到内存并返回一个类的对象。
        // JRebel热部署工具，其实就是自动拿到ClassLoader调用loadClass把类从磁盘中加载到内存中去。
        Class clazz = T05_CustomClassLoader.class.getClassLoader().loadClass("com.gate.jvm.T02_class");
        // 输出：发现返回了加载的类，就代表类已经被加载了。
//        System.out.println(clazz);

        ClassLoader l = new T05_CustomClassLoader();
        Class clazz1 = l.loadClass("com.gate.jvm.T02_class");
        clazz1.newInstance();
        System.out.println(l.getClass().getClassLoader());
        System.out.println(l.getParent());
    }
}

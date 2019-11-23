package com.gate.designpatterns;

import com.gate.Tools.AbstarctFactory.*;

/**
 * 抽象工厂
 * 抽象工厂方便产品族内的小产品的扩展，比如Color里现在有ColorImpl01,ColorImpl02，要扩展ColorImpl03.
 *
 * 抽象工厂方便扩展产品族内的产品，工厂方法方便扩展新产品加入产品族。
 *
 * 步骤：
 * 1.为Shape创建接口：Shape.java
 * 2.创建Shape接口的实现类：ShapeImpl01.java/ShapeImpl02.java/ShapeImpl03.java
 * 3.为Color创建接口：Color.java
 * 4.创建Color接口的实现类：ColorImpl01.java/ColorImpl02.java/ColorImpl03.java
 * 5.为Color和Shape创建抽象类来获取工厂：AbstractFactory.java
 * 6.创建扩展了AbstractFactory的工厂类，基于给定的信息生成实体类的对象：ShapeFactory.java/ColorFactory.java
 * 7.创建一个工厂创造器/工厂生成类，通过传递Color和Shape信息来获取工厂：FactoryProducer.java
 * 8.使用FactoryProducer来获取AbstractFactory，通过传递类型信息获取实体类的对象。下面的main()方法。
 *
 */
public class T03_AbstractFactory {

    public static void main(String[] args) {

        // 获取Shape工厂
        AbstractFactory shapeFactory = new FactoryProducer().getFactory("SHAPE");
        // 获取Shape为ShapeImpl01的对象
        Shape shape1 = shapeFactory.getShape("ShapeImpl01");
        // 调用ShapeImpl01的方法
        shape1.draw();
        Shape shape2 = shapeFactory.getShape("ShapeImpl02");
        shape2.draw();
        Shape shape3 = shapeFactory.getShape("ShapeImpl03");
        shape3.draw();

        // 获取Color工厂
        AbstractFactory colorFactory = new FactoryProducer().getFactory("COLOR");
        // 获取Color为ColorImpl01的对象
        Color color01 = colorFactory.getColor("ColorImpl01");
        // 调用ColorImpl01的方法
        color01.fill();
        Color color02 = colorFactory.getColor("ColorImpl02");
        color02.fill();
        Color color03 = colorFactory.getColor("ColorImpl03");
        color03.fill();
        Color color04 = colorFactory.getColor("ColorImpl04");
        color04.fill();

        // 获取Mode工厂
        AbstractFactory modeFactory = new FactoryProducer().getFactory("MODE");
        // 获取Mode为ModeImpl01的对象
        Mode mode01 = modeFactory.getMode("ModeImpl01");
        // 调用ModeImpl01的方法
        mode01.mode();
        // 获取Mode为ModeImpl01的对象
        Mode mode02 = modeFactory.getMode("ModeImpl02");
        // 调用ModeImpl01的方法
        mode02.mode();
        // 获取Mode为ModeImpl01的对象
        Mode mode03 = modeFactory.getMode("ModeImpl03");
        // 调用ModeImpl01的方法
        mode03.mode();
    }
}

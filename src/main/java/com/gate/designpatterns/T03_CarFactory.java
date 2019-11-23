package com.gate.designpatterns;

import com.gate.Tools.Car;
import com.gate.Tools.Moveable;

/**
 * 工厂方法
 * 工厂方法比较方便扩展产品。比如现在有一个Color、Shape，要扩展一个Mode，只要加新产品加工厂就好了。
 *
 * 如果添加新的交通工具就需要添加新的，比如PlaneFactory
 */
public class T03_CarFactory {

    // 更完美的写法，它们的返回值都可以是Moveable
    //public Car createCar() {
    public Moveable createCar() {
        System.out.println("car created");
        return new Car();
    }
}

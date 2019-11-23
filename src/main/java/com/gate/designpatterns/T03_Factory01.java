package com.gate.designpatterns;

import com.gate.Tools.Moveable;

/**
 * 简单工厂、静态工厂、工厂方法、抽象工厂、Spring IOC.
 *
 * 任何可以产生对象的方法或者类，都可以称之为工厂。
 * 一个类如果返回的是个对象，就可以称之为工厂。
 * 单例也是工厂，可以称之为静态工厂。
 *
 * Q:为什么有了new，还要有工厂？
 * A:灵活控制对象生产过程。比如谁有权限访问、谁有权限修饰。
 *
 * 这个Java类是对多态的运用。
 *
 */
public class T03_Factory01 {

    public static void main(String[] args) {
        // 想开Car就new Car，想开Car就new Plane。这样扩展性不强。
//        Car car = new Car();
//        car.go();
//        Plane plane = new Plane();
//        plane.go();

        // 给Car和Plane创建一个父接口，然后Car和Plane实现这个父接口
        // 想开Plane就开new Plane(). 想开Car就new Car().
//        Moveable moveable = new Plane();
//        moveable.go();

        // 工厂方法
        Moveable moveable = new T03_CarFactory().createCar();
    }

}

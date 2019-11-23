package com.gate.designpatterns;

import com.gate.Tools.Car;
import com.gate.Tools.Plane;

/**
 * 简单工厂
 *
 * 扩展性不好，因为如果添加一个新的交通工具，下面代码就要加一个。实现也要写死。
 * 每种产品对应一个工厂。
 */
public class T03_SimpleFactory {

    public Car creatCar(){
        // 在前后都可以加代码进行处理
        // Before processing
        return new Car();
    }

    public Plane creatPlane() {
        return new Plane();
    }
}

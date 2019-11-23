package com.gate.Tools.AbstarctFactory;

public class FactoryProducer {

    public static AbstractFactory getFactory(String choice) {

        if(choice.equalsIgnoreCase("SHAPE")) {
            return new ShapeFactory();
        } else if(choice.equalsIgnoreCase("COLOR")){
            return new ColorFactory();
        } else if(choice.equalsIgnoreCase("MODE")) {
            return new ModeFactory();
        }
        return null;
    }
}

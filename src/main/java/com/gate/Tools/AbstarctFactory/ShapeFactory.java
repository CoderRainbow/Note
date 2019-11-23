package com.gate.Tools.AbstarctFactory;

public class ShapeFactory extends AbstractFactory {

    @Override
    public Shape getShape(String shape) {
        if("".equals(shape) || shape.equals(null)){
            return null;
        } else if(shape.equalsIgnoreCase("ShapeImpl01")){
            return new ShapeImpl01();
        } else if(shape.equalsIgnoreCase("ShapeImpl02")){
            return new ShapeImpl02();
        } else if(shape.equalsIgnoreCase("ShapeImpl03")){
            return new ShapeImpl03();
        }
        return null;
    }

    @Override
    public Mode getMode(String mode) {
        return null;
    }

    @Override
    public Color getColor(String color) {
        return null;
    }

}

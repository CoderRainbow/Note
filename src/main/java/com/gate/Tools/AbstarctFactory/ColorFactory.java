package com.gate.Tools.AbstarctFactory;

public class ColorFactory extends AbstractFactory {
    @Override
    public Color getColor(String color) {

        if("".equals(color) || color.equals(null)) {
            return null;
        } else if(color.equalsIgnoreCase("ColorImpl01")) {
            return new ColorImpl01();
        } else if(color.equalsIgnoreCase("ColorImpl02")) {
            return new ColorImpl02();
        } else if(color.equalsIgnoreCase("ColorImpl03")) {
            return new ColorImpl03();
        } else if(color.equalsIgnoreCase("ColorImpl04")) {
            return new ColorImpl04();
        }
        return null;
    }

    @Override
    public Shape getShape(String shape) {
        return null;
    }

    @Override
    public Mode getMode(String mode) {
        return null;
    }
}

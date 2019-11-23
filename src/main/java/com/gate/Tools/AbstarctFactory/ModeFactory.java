package com.gate.Tools.AbstarctFactory;

public class ModeFactory extends AbstractFactory{

    @Override
    public Mode getMode(String mode) {
        if("".equalsIgnoreCase(mode) || mode.equalsIgnoreCase(null)){
            return null;
        } else if(mode.equalsIgnoreCase("ModeImpl01")){
            return new ModeImpl01();
        } else if(mode.equalsIgnoreCase("ModeImpl02")){
            return new ModeImpl02();
        } else if(mode.equalsIgnoreCase("ModeImpl03")){
            return new ModeImpl03();
        }
        return null;
    }

    @Override
    public Color getColor(String color) {
        return null;
    }

    @Override
    public Shape getShape(String shape) {
        return null;
    }

}

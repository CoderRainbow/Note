package com.gate.Tools.AbstarctFactory;

public abstract class AbstractFactory {
    public abstract Color getColor(String color);
    public abstract Shape getShape(String shape);
    public abstract Mode getMode(String mode);
}

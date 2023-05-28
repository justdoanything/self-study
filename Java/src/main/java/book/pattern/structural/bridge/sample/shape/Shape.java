package book.pattern.structural.bridge.sample.shape;

import book.pattern.structural.bridge.sample.color.Color;

public abstract class Shape {
    protected Color color;

    public Shape(Color color){
        this.color = color;
    }

    abstract public void applyColor();
}

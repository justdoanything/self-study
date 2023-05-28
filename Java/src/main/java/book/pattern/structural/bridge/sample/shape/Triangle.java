package book.pattern.structural.bridge.sample.shape;

import book.pattern.structural.bridge.sample.color.Color;

public class Triangle extends Shape {
    public Triangle(Color color) {
        super(color);
    }

    @Override
    public void applyColor() {
        System.out.println("Triangle filled with color");
        color.applyColor();
    }
}

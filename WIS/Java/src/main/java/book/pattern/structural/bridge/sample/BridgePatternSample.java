package book.pattern.structural.bridge.sample;

import book.pattern.structural.bridge.sample.color.GreenColor;
import book.pattern.structural.bridge.sample.color.RedColor;
import book.pattern.structural.bridge.sample.shape.Pentagon;
import book.pattern.structural.bridge.sample.shape.Shape;
import book.pattern.structural.bridge.sample.shape.Triangle;

public class BridgePatternSample {
    public static void main(String[] args) {
        Shape triangle = new Triangle(new RedColor());
        triangle.applyColor();

        Shape pentagon = new Pentagon(new GreenColor());
        pentagon.applyColor();
    }

}

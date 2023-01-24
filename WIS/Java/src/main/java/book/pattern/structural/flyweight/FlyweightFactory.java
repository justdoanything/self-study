package book.pattern.structural.flyweight;

import java.util.HashMap;

public class FlyweightFactory {
    public enum ShapeTypes {
        OVAL, OVAL_FILL, LINE;
    }
    public static final HashMap<ShapeTypes, Shape> shapes = new HashMap<>();

    public static Shape getShape(ShapeTypes shapeTypes) {
        Shape shape = shapes.get(shapeTypes);

        if(shape == null) {
            switch (shapeTypes){
                case LINE:
                    shape = new Line();
                    break;
                case OVAL:
                    shape = new Oval(false);
                    break;
                case OVAL_FILL:
                    shape = new Oval(true);
                    break;
                default:
                    throw new RuntimeException("Shape Types Error.");
            }
            shapes.put(shapeTypes, shape);
        }
        return shape;
    }
}

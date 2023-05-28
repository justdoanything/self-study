package book.pattern.structural.flyweight;

import java.awt.Color;
import java.awt.Graphics;

public interface Shape {
    public void draw(Graphics graphics, int x, int y, int width, int height, Color color);
}

package book.pattern.structural.flyweight;

import java.awt.Color;
import java.awt.Graphics;

public class Line implements Shape {

    public Line() {
        System.out.println("Create Line");
        try{
            Thread.sleep(2000);
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void draw(Graphics graphics, int x, int y, int width, int height, Color color) {
        graphics.setColor(color);
        graphics.drawLine(x, y, width, height);
    }
}

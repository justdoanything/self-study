package book.pattern.structural.flyweight;

import java.awt.Color;
import java.awt.Graphics;

public class Oval implements Shape {
    // 내적 속성 (intrinsic Property)
    private boolean fill;

    public Oval(boolean fill) {
        this.fill = fill;
        System.out.println("Create Oval with fill : " + fill);
        try {
            Thread.sleep(2000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    @Override
    public void draw(Graphics graphics, int x, int y, int width, int height, Color color) {
        graphics.setColor(color);
        graphics.drawOval(x, y, width, height);
        if(fill){
            graphics.fillOval(x, y, width, height);
        }
    }
}

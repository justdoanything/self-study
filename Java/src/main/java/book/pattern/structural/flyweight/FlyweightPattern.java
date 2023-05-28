package book.pattern.structural.flyweight;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FlyweightPattern extends JFrame {
    private final int width;
    private final int height;

    private static final FlyweightFactory.ShapeTypes shapes[] = { FlyweightFactory.ShapeTypes.LINE, FlyweightFactory.ShapeTypes.OVAL, FlyweightFactory.ShapeTypes.OVAL_FILL };
    private static final Color colors[] = { Color.RED, Color.GREEN, Color.YELLOW };

    public FlyweightPattern(int width, int height) {
        this.width = width;
        this.height = height;
        Container container = getContentPane();

        JButton jButton = new JButton("Draw");
        final JPanel jPanel = new JPanel();

        container.add(jPanel, BorderLayout.CENTER);
        container.add(jButton, BorderLayout.SOUTH);
        setSize(width, height);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Graphics graphics = jPanel.getGraphics();
                for(int i=0; i<20; i++){
                    Shape shape = FlyweightFactory.getShape(getRandomShape());
                    shape.draw(graphics, getRandomX(), getRandomY(), getRandomWidth(), getRandomHeight(), getRandomColor());
                }
            }
        });
    }

    private FlyweightFactory.ShapeTypes getRandomShape() {
        return shapes[ (int) (Math.random() * shapes.length) ];
    }

    private int getRandomX() {
        return (int) (Math.random() * width);
    }

    private int getRandomY() {
        return (int) (Math.random() * height);
    }

    private int getRandomWidth() {
        return (int) (Math.random() * (width / 10));
    }

    private int getRandomHeight() {
        return (int) (Math.random() * (height / 10));
    }

    private Color getRandomColor() {
        return colors[(int) (Math.random() * colors.length)];
    }

    public static void main(String[] args) {
        FlyweightPattern drawing = new FlyweightPattern(500,600);
    }
}

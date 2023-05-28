package book.pattern.structural.bridge.sample.color;

public class RedColor implements Color {
    @Override
    public void applyColor() {
        System.out.println("This is red color");
    }
}

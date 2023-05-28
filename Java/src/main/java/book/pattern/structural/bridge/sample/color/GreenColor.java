package book.pattern.structural.bridge.sample.color;

public class GreenColor implements Color {
    @Override
    public void applyColor() {
        System.out.println("This is green color");
    }
}

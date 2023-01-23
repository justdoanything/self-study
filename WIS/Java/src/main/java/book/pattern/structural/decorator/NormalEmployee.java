package book.pattern.structural.decorator;

public class NormalEmployee implements Employee {
    @Override
    public void assemble() {
        System.out.println("Normal Employee.");
    }
}

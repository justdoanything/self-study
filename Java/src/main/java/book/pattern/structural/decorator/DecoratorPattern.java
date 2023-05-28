package book.pattern.structural.decorator;

public class DecoratorPattern {
    public static void main(String[] args) {
        Employee devEmployee = new DevEmployee(new NormalEmployee());
        devEmployee.assemble();
        System.out.println("********************");

        Employee multiEmployee = new DevEmployee(new TestEmployee(new NormalEmployee()));
        multiEmployee.assemble();
    }
}

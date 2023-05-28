package book.pattern.structural.decorator;

public class TestEmployee extends EmployeeDecorator {
    public TestEmployee(Employee employee){
        super(employee);
    }

    @Override
    public void assemble() {
        super.assemble();
        System.out.println("  Adding features of Test Employee.");
    }
}

package book.pattern.structural.decorator;

public class DevEmployee extends EmployeeDecorator {
    public DevEmployee(Employee employee){
        super(employee);
    }

    @Override
    public void assemble() {
        super.assemble();
        System.out.println("  Adding features of Dev Employee.");
    }
}

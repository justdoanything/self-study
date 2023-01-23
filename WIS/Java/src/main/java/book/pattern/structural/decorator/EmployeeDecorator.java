package book.pattern.structural.decorator;

public class EmployeeDecorator implements Employee {
    protected Employee employee;

    public  EmployeeDecorator(Employee employee) {
        this.employee = employee;
    }

    @Override
    public void assemble() {
        this.employee.assemble();
    }
}

package book.pattern.creational.factory;

import book.pattern.creational.factory.model.Employee;
import book.pattern.creational.factory.model.Tester;

public class TesterFactory implements EmployeeAbstractFactory {
    private String id;
    private String name;
    private String department;

    public TesterFactory(String id, String name, String department) {
        this.id = id;
        this.name = name;
        this.department = department;
    }

    @Override
    public Employee createEmployee() {
        return new Tester(id, name, department);
    }
}
